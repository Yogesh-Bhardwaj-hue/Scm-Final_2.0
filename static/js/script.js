AOS.init({
    duration: 1000,
    once: true,
});


const navbar = document.getElementById('navbar');
const navLinks = document.querySelector('.nav-links');
const authButtons = document.querySelector('.auth-buttons');
const heroSlider = document.querySelector('.hero-slider');
const hamburger = document.querySelector('.hamburger');

window.addEventListener('scroll', () => {
    if (window.scrollY > 100) {
        navbar.classList.add('scrolled');
    } else {
        navbar.classList.remove('scrolled');
    }
});

hamburger.addEventListener('click', () => {
    navLinks.classList.toggle('active');
    authButtons.classList.toggle('active');
    hamburger.classList.toggle('active');
});

let currentSlide = 0;
const slides = document.querySelectorAll('.slide');

function showSlide(n) {
    slides[currentSlide].classList.remove('active');
    currentSlide = (n + slides.length) % slides.length;
    slides[currentSlide].classList.add('active');
    heroSlider.style.transform = `translateX(-${currentSlide * 100}%)`;
}

setInterval(() => showSlide(currentSlide + 1), 4000);

const stats = document.querySelectorAll('.stat-number');
const statsSection = document.querySelector('.stats-section');

const animateStats = () => {
    stats.forEach(stat => {
        const target = parseInt(stat.getAttribute('data-target'));
        const count = parseInt(stat.innerText);
        const increment = target / 200;

        if (count < target) {
            stat.innerText = Math.ceil(count + increment);
            setTimeout(animateStats, 100);
        } else {
            stat.innerText = target;
        }
    });
};

const isInViewport = (element) => {
    const rect = element.getBoundingClientRect();
    return (
        rect.top >= 0 &&
        rect.left >= 0 &&
        rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
        rect.right <= (window.innerWidth || document.documentElement.clientWidth)
    );
};

window.addEventListener('scroll', () => {
    if (isInViewport(statsSection)) {
        animateStats();
    }
});

const faqItems = document.querySelectorAll('.faq-item');

faqItems.forEach(item => {
    const question = item.querySelector('.faq-question');
    question.addEventListener('click', () => {
        item.classList.toggle('active');
    });
});

let blogs = JSON.parse(localStorage.getItem('blogs')) || [];
const blogsContainer = document.getElementById('blogs-container');
const addBlogBtn = document.getElementById('add-blog-btn');
const addBlogModal = document.getElementById('add-blog-modal');
const addBlogForm = document.getElementById('add-blog-form');
const closeButtons = document.querySelectorAll('.close');
const searchBar = document.getElementById('search-bar');
const categoryFilter = document.getElementById('category-filter');
const readMoreModal = document.getElementById('read-more-modal');
const fullBlogContent = document.getElementById('full-blog-content');
const prevPageBtn = document.getElementById('prev-page');
const nextPageBtn = document.getElementById('next-page');
const pageNumbers = document.getElementById('page-numbers');

let currentPage = 1;
const blogsPerPage = 6;

const displayBlogs = (blogsToDisplay) => {
    blogsContainer.innerHTML = '';
    blogsToDisplay.forEach(blog => {
        const blogPost = document.createElement('div');
        blogPost.classList.add('blog-post');
        blogPost.innerHTML = `
            <img src="${blog.images[0]}" alt="${blog.title}">
            <div class="blog-post-content">
                <span class="category">${blog.category}</span>
                <h3>${blog.title}</h3>
                <p>${blog.shortDescription}</p>
                <a href="#" class="read-more" data-id="${blog.id}">Read More</a>
                <a href="#" class="delete-btn" data-id="${blog.id}">Delete</a>
            </div>
        `;
        blogsContainer.appendChild(blogPost);
    });

    document.querySelectorAll('.read-more').forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            const blogId = parseInt(e.target.getAttribute('data-id'));
            const blog = blogs.find(b => b.id === blogId);
            showFullBlog(blog);
        });
    });

    document.querySelectorAll('.delete-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            const blogId = parseInt(e.target.getAttribute('data-id'));
            
            if (confirm("Are you sure you want to delete this blog?")) {
                window.location.href = `/blogs/delete/${blogId}/`;
            }
        });
    });
};

const showFullBlog = (blog) => {
    fullBlogContent.innerHTML = `
        <h2>${blog.title}</h2>
        <h3>${blog.subtitle}</h3>
        ${blog.images.map(img => `<img src="${img}" alt="${blog.title}">`).join('')}
        <p>${blog.longDescription}</p>
        ${blog.bulletPoints ? `
            <h4>Key Points:</h4>
            <ul>
                ${blog.bulletPoints.map(point => `<li>${point}</li>`).join('')}
            </ul>
        ` : ''}
        ${blog.youtubeLink ? `
            <h4>Related Video:</h4>
            <iframe width="560" height="315" src="${blog.youtubeLink}" frameborder="0" allowfullscreen></iframe>
        ` : ''}
    `;
    readMoreModal.style.display = 'block';
};

const filterBlogs = () => {
    const searchTerm = searchBar.value.toLowerCase();
    const category = categoryFilter.value;
    
    const filteredBlogs = blogs.filter(blog => 
        (blog.title.toLowerCase().includes(searchTerm) || blog.shortDescription.toLowerCase().includes(searchTerm)) &&
        (category === '' || blog.category === category)
    );

    currentPage = 1;
    updatePagination(filteredBlogs);
    displayBlogs(getPaginatedBlogs(filteredBlogs));
};

const getPaginatedBlogs = (blogsArray = blogs) => {
    const startIndex = (currentPage - 1) * blogsPerPage;
    const endIndex = startIndex + blogsPerPage;
    return blogsArray.slice(startIndex, endIndex);
};

const updatePagination = (blogsArray = blogs) => {
    const totalPages = Math.ceil(blogsArray.length / blogsPerPage);
    pageNumbers.textContent = `Page ${currentPage} of ${totalPages}`;
    prevPageBtn.disabled = currentPage === 1;
    nextPageBtn.disabled = currentPage === totalPages;
};

prevPageBtn.addEventListener('click', () => {
    if (currentPage > 1) {
        currentPage--;
        updatePagination();
        displayBlogs(getPaginatedBlogs());
    }
});

nextPageBtn.addEventListener('click', () => {
    const totalPages = Math.ceil(blogs.length / blogsPerPage);
    if (currentPage < totalPages) {
        currentPage++;
        updatePagination();
        displayBlogs(getPaginatedBlogs());
    }
});

addBlogBtn.addEventListener('click', () => {
    addBlogModal.style.display = 'block';
});

closeButtons.forEach(button => {
    button.addEventListener('click', () => {
        addBlogModal.style.display = 'none';
        readMoreModal.style.display = 'none';
    });
});

window.addEventListener('click', (e) => {
    if (e.target === addBlogModal || e.target === readMoreModal) {
        addBlogModal.style.display = 'none';
        readMoreModal.style.display = 'none';
    }
});

const imagePreview = document.getElementById('image-preview');
const blogImagesInput = document.getElementById('blog-images');

blogImagesInput.addEventListener('change', (e) => {
    imagePreview.innerHTML = '';
    const files = e.target.files;
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        const reader =

 new FileReader();
        reader.onload = (e) => {
            const img = document.createElement('img');
            img.src = e.target.result;
            imagePreview.appendChild(img);
        };
        reader.readAsDataURL(file);
    }
});

addBlogForm.addEventListener('submit', (e) => {
    e.preventDefault();
    
    // Create FormData for file upload
    const formData = new FormData();
    formData.append('title', document.getElementById('blog-title').value);
    formData.append('subtitle', document.getElementById('blog-subtitle').value);
    formData.append('short_description', document.getElementById('blog-short-desc').value);
    formData.append('long_description', document.getElementById('blog-long-desc').value);
    formData.append('category', document.getElementById('blog-category').value);
    formData.append('bullet_points', document.getElementById('blog-bullet-points').value);
    formData.append('youtube_link', document.getElementById('blog-youtube-link').value);
    
    // Add images
    const fileInput = document.getElementById('blog-images');
    for (let i = 0; i < fileInput.files.length; i++) {
        formData.append('images', fileInput.files[i]);
    }
    
    // Add CSRF token
    const csrfToken = document.querySelector('[name=csrfmiddlewaretoken]').value;
    
    // Send data to Django
    fetch('/blogs/add/', {
        method: 'POST',
        body: formData,
        headers: {
            'X-CSRFToken': csrfToken
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.status === 'success') {
            addBlogModal.style.display = 'none';
            addBlogForm.reset();
            imagePreview.innerHTML = '';
            window.location.reload(); // Reload to see the new blog
        }
    })
    .catch(error => console.error('Error:', error));
});

searchBar.addEventListener('input', filterBlogs);
categoryFilter.addEventListener('change', filterBlogs);

updatePagination();
displayBlogs(getPaginatedBlogs());

const testimonialSlider = document.querySelector('.testimonial-slider');
let isDown = false;
let startX;
let scrollLeft;

testimonialSlider.addEventListener('mousedown', (e) => {
    isDown = true;
    startX = e.pageX - testimonialSlider.offsetLeft;
    scrollLeft = testimonialSlider.scrollLeft;
});

testimonialSlider.addEventListener('mouseleave', () => {
    isDown = false;
});

testimonialSlider.addEventListener('mouseup', () => {
    isDown = false;
});

testimonialSlider.addEventListener('mousemove', (e) => {
    if (!isDown) return;
    e.preventDefault();
    const x = e.pageX - testimonialSlider.offsetLeft;
    const walk = (x - startX) * 2;
    testimonialSlider.scrollLeft = scrollLeft - walk;
});

const contactForm = document.getElementById('contact-form');

contactForm.addEventListener('submit', (e) => {
    e.preventDefault();
    alert('Thank you for your message! We will get back to you soon.');
    contactForm.reset();
});

// Add some default blogs if there are none
if (blogs.length === 0) {
    const defaultBlogs = [
        {
            id: 1,
            title: "The Future of AI in Content Creation",
            subtitle: "How artificial intelligence is revolutionizing the way we create and consume content",
            shortDescription: "Explore the impact of AI on content creation and its potential future applications.",
            longDescription: "Artificial Intelligence is rapidly changing the landscape of content creation. From automated writing assistants to AI-generated images and videos, the possibilities seem endless. This blog post delves into the current state of AI in content creation, its benefits and challenges, and what the future might hold for content creators and consumers alike.",
            category: "technology",
            bulletPoints: ["AI-powered writing tools", "Machine learning in image and video generation", "Ethical considerations of AI-generated content", "The role of human creativity in an AI-driven world"],
            youtubeLink: "https://www.youtube.com/embed/dQw4w9WgXcQ",
            images: ["./images/b1.jpg"]
        },
        {
            id: 2,
            title: "Mastering the Art of Mindfulness",
            subtitle: "Simple techniques for a more balanced and focused life",
            shortDescription: "Learn how to incorporate mindfulness into your daily routine for improved well-being.",
            longDescription: "In our fast-paced world, mindfulness has become an essential skill for maintaining mental health and overall well-being. This comprehensive guide introduces you to the concept of mindfulness, its benefits, and practical techniques you can use to become more present and aware in your daily life. From meditation exercises to mindful eating, discover how small changes can lead to significant improvements in your mental clarity, stress levels, and overall happiness.",
            category: "lifestyle",
            bulletPoints: ["Understanding mindfulness", "Basic meditation techniques", "Mindful eating and movement", "Incorporating mindfulness into daily activities"],
            youtubeLink: "https://www.youtube.com/embed/dQw4w9WgXcQ",
            images: ["./images/b2.jpg"]
        },
        {
            id: 3,
            title: "Hidden Gems: Off-the-Beaten-Path Travel Destinations",
            subtitle: "Discover unique and less-crowded places for your next adventure",
            shortDescription: "Explore lesser-known travel destinations that offer unique experiences away from tourist crowds.",
            longDescription: "Tired of overcrowded tourist hotspots? This blog post takes you on a journey to some of the world's best-kept travel secrets. From remote islands to charming small towns, we've curated a list of destinations that offer authentic experiences, stunning natural beauty, and rich cultural heritage - all without the crowds. Get ready to add some new and exciting locations to your travel bucket list!",
            category: "travel",
            bulletPoints: ["Secluded beaches in Southeast Asia", "Charming villages in Eastern Europe", "Unexplored national parks in South America", "Cultural experiences in lesser-known African countries"],
            youtubeLink: "https://www.youtube.com/embed/dQw4w9WgXcQ",
            images: ["./images/b3.jpg"]
        }
    ];
    blogs = defaultBlogs;
    localStorage.setItem('blogs', JSON.stringify(blogs));
    updatePagination();
    displayBlogs(getPaginatedBlogs());
}




