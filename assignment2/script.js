const pages = {
  home: () => `
    <div class="page">
      <div class="page-eyebrow">Welcome Back</div>
      <h2 class="page-title">Your Trusted<br>Financial Partner</h2>
      <div class="gold-rule"></div>
      <div class="cards-row">
        <div class="card">
          <div class="card-icon">🏦</div>
          <div class="card-label">Branches</div>
          <div class="card-value">200+</div>
          <div class="card-sub">Across the country</div>
        </div>
        <div class="card">
          <div class="card-icon">👥</div>
          <div class="card-label">Customers</div>
          <div class="card-value">1.2M+</div>
          <div class="card-sub">Trusted accounts</div>
        </div>
        <div class="card">
          <div class="card-icon">🔒</div>
          <div class="card-label">Security</div>
          <div class="card-value">256-bit</div>
          <div class="card-sub">End-to-end encrypted</div>
        </div>
      </div>
      <div class="welcome-strip">
        <p>Experience the future of banking — secure, fast, and always available. Open an account or sign in to manage your finances.</p>
        <button class="strip-btn" onclick="loadPage('login', document.querySelector('.nav-link:nth-child(5)'))">Get Started →</button>
      </div>
    </div>`,

  about: () => `
    <div class="page">
      <div class="page-eyebrow">Our Story</div>
      <h2 class="page-title">Three Decades of<br>Digital Banking</h2>
      <div class="gold-rule"></div>
      <p class="page-lead">Founded in 1995 with a bold vision — to bring banking to every doorstep. We have grown into one of the most trusted financial institutions, serving over a million customers with integrity and innovation.</p>
      <div class="about-grid">
        <div class="about-stat"><div class="num">30+</div><div class="lbl">Years of Service</div></div>
        <div class="about-stat"><div class="num">1.2M</div><div class="lbl">Happy Customers</div></div>
        <div class="about-stat"><div class="num">200+</div><div class="lbl">Branch Locations</div></div>
        <div class="about-stat"><div class="num">99.9%</div><div class="lbl">Uptime Guarantee</div></div>
      </div>
      <p class="about-text">We believe banking should be simple, transparent, and accessible. Our digital-first approach has enabled millions to manage their finances from the comfort of their homes, while our expert teams remain available around the clock for personalized support.</p>
    </div>`,

  services: () => `
    <div class="page">
      <div class="page-eyebrow">What We Offer</div>
      <h2 class="page-title">Our Services</h2>
      <div class="gold-rule"></div>
      <p class="page-lead">Comprehensive financial products crafted to meet every need — from personal savings to business growth.</p>
      <div class="services-list">
        <div class="service-item">
          <div class="service-badge">🏠</div>
          <div class="service-info">
            <h3>Home & Personal Loans</h3>
            <p>Competitive interest rates with flexible repayment tenures tailored to your lifestyle.</p>
          </div>
          <div class="service-tag">Apply Now</div>
        </div>
        <div class="service-item">
          <div class="service-badge">💰</div>
          <div class="service-info">
            <h3>Fixed Deposits</h3>
            <p>Grow your savings with guaranteed returns and zero-risk investment options.</p>
          </div>
          <div class="service-tag">Learn More</div>
        </div>
        <div class="service-item">
          <div class="service-badge">📋</div>
          <div class="service-info">
            <h3>Account Opening</h3>
            <p>Open a savings or current account in minutes, entirely online, with zero paperwork.</p>
          </div>
          <div class="service-tag">Start Today</div>
        </div>
        <div class="service-item">
          <div class="service-badge">💳</div>
          <div class="service-info">
            <h3>Net Banking & Cards</h3>
            <p>Seamless digital transactions, premium debit & credit cards, and real-time alerts.</p>
          </div>
          <div class="service-tag">Explore</div>
        </div>
      </div>
    </div>`,

  login: () => `
    <div class="auth-wrap">
      <div class="auth-card">
        <div class="auth-logo">O</div>
        <h2>Welcome Back</h2>
        <p class="auth-sub">Sign in to your OurBank account</p>
        <div class="field">
          <label>Username</label>
          <input type="text" placeholder="Enter your username">
        </div>
        <div class="field">
          <label>Password</label>
          <input type="password" placeholder="••••••••">
        </div>
        <button class="auth-btn">Sign In</button>
        <p class="auth-switch">New customer? <span onclick="loadPage('signup')">Create an Account</span></p>
      </div>
    </div>`,

  signup: () => `
    <div class="auth-wrap">
      <div class="auth-card">
        <div class="auth-logo">O</div>
        <h2>Create Account</h2>
        <p class="auth-sub">Join OurBank in less than 2 minutes</p>
        <div class="field">
          <label>Full Name</label>
          <input type="text" placeholder="Your full name">
        </div>
        <div class="field">
          <label>Email Address</label>
          <input type="email" placeholder="you@example.com">
        </div>
        <div class="field">
          <label>Password</label>
          <input type="password" placeholder="Create a strong password">
        </div>
        <div class="field">
          <label>Confirm Password</label>
          <input type="password" placeholder="Repeat password">
        </div>
        <button class="auth-btn">Create Account</button>
        <p class="auth-switch">Already a member? <span onclick="loadPage('login', document.querySelector('.nav-link:nth-child(5)'))">Sign In</span></p>
      </div>
    </div>`,

  contact: () => `
    <div class="page">
      <div class="page-eyebrow">Get in Touch</div>
      <h2 class="page-title">We're Here for You</h2>
      <div class="gold-rule"></div>
      <p class="page-lead">Our support team is available around the clock. Reach us through any channel that suits you best.</p>
      <div class="contact-grid">
        <div class="contact-card">
          <div class="c-icon">✉</div>
          <div class="c-label">Email Support</div>
          <div class="c-value">support@ourbank.com</div>
        </div>
        <div class="contact-card">
          <div class="c-icon">📞</div>
          <div class="c-label">Phone</div>
          <div class="c-value">9876 543 210</div>
        </div>
        <div class="hours-box">
          <h3>Banking Hours</h3>
          <div class="hours-row"><span>Monday – Friday</span><span>9:00 AM – 6:00 PM</span></div>
          <div class="hours-row"><span>Saturday</span><span>9:00 AM – 2:00 PM</span></div>
          <div class="hours-row"><span>Net Banking</span><span>24 / 7</span></div>
        </div>
      </div>
    </div>`
};

function loadPage(id, linkEl) {
  document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
  if (linkEl) linkEl.classList.add('active');
  const area = document.getElementById('contentArea');
  area.innerHTML = (pages[id] || pages.home)();
}

// Load home on start
loadPage('home', document.querySelector('.nav-link'));