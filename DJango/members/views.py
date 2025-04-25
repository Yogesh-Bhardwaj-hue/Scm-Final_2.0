import json
from django.shortcuts import render, redirect, get_object_or_404
from django.http import HttpResponse, JsonResponse
from django.template import loader
from .models import Member, Bid, Item
from django.contrib.auth.forms import UserCreationForm
from django.contrib import messages
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth.decorators import login_required, user_passes_test
from django.utils import timezone

def members(request):
    member_list = Member.objects.all().values()
    template = loader.get_template('members/members.html')  # Fix template path
    context = {
        'members': member_list,
    }
    return HttpResponse(template.render(context, request))

def register(request):
    if request.method == 'POST':
        form = UserCreationForm(request.POST)
        if form.is_valid():
            form.save()
            username = form.cleaned_data.get('username')
            messages.success(request, f'Account created for {username}!')
            return redirect('login')
    else:
        form = UserCreationForm()
    return render(request, 'members/register.html', {'form': form})

@login_required
def item_detail(request, item_id):
    item = get_object_or_404(Item, pk=item_id)
    bids = Bid.objects.filter(item=item).order_by('-amount')
    
    # Check if the user can close this bid
    can_close_bid = request.user.is_staff or request.user.groups.filter(name='Subscribers').exists()
    
    context = {
        'item': item,
        'bids': bids,
        'highest_bid': bids.first() if bids.exists() else None,
        'can_close_bid': can_close_bid,
        'is_winner': item.status == 'closed' and item.winner == request.user
    }
    return render(request, 'members/item_detail.html', context)

@login_required
@csrf_exempt
def place_bid(request, item_id):
    if request.method != 'POST':
        return JsonResponse({'error': 'Only POST method is allowed'}, status=405)
    
    data = json.loads(request.body)
    amount = data.get('amount')
    
    if not amount:
        return JsonResponse({'error': 'Bid amount is required'}, status=400)
    
    try:
        item = Item.objects.get(pk=item_id)
        
        # Check if the auction is closed
        if item.status == 'closed':
            return JsonResponse({'error': 'This auction is closed'}, status=400)
        
        highest_bid = Bid.objects.filter(item=item).order_by('-amount').first()
        
        if highest_bid and float(amount) <= highest_bid.amount:
            return JsonResponse({'error': 'Bid must be higher than current highest bid'}, status=400)
        
        bid = Bid.objects.create(
            user=request.user,
            item=item,
            amount=amount
        )
        
        return JsonResponse({
            'success': True,
            'bid': {
                'user': bid.user.username,
                'amount': float(bid.amount),
                'timestamp': bid.timestamp.strftime('%Y-%m-%d %H:%M:%S')
            }
        })
        
    except Item.DoesNotExist:
        return JsonResponse({'error': 'Item not found'}, status=404)
    except Exception as e:
        return JsonResponse({'error': str(e)}, status=500)

@login_required
def get_bids(request, item_id):
    try:
        item = Item.objects.get(pk=item_id)
        bids = Bid.objects.filter(item=item).order_by('-timestamp')
        
        bids_data = [{
            'user': bid.user.username,
            'amount': float(bid.amount),
            'timestamp': bid.timestamp.strftime('%Y-%m-%d %H:%M:%S')
        } for bid in bids]
        
        return JsonResponse({'bids': bids_data})
        
    except Item.DoesNotExist:
        return JsonResponse({'error': 'Item not found'}, status=404)

# Add this function to check if a user is a subscriber or admin
def is_subscriber_or_admin(user):
    return user.is_staff or user.groups.filter(name='Subscribers').exists()

# Add this view to close bids
@login_required
@user_passes_test(is_subscriber_or_admin)
def close_bid(request, item_id):
    if request.method != 'POST':
        return redirect('item_detail', item_id=item_id)
    
    item = get_object_or_404(Item, pk=item_id)
    
    # Check if the auction is already closed
    if item.status == 'closed':
        messages.error(request, 'This auction is already closed.')
        return redirect('item_detail', item_id=item_id)
    
    # Find the highest bid
    highest_bid = Bid.objects.filter(item=item).order_by('-amount').first()
    
    # Close the auction and set the winner
    item.status = 'closed'
    if highest_bid:
        item.winner = highest_bid.user
    item.save()
    
    messages.success(request, 'Auction closed successfully! The winner has been notified.')
    return redirect('item_detail', item_id=item_id)

def item_list(request):
    items = Item.objects.all()
    return render(request, 'members/item_list.html', {'items': items})