const API = 'http://localhost:8080';

// All form IDs
const forms = {
    login:  document.getElementById('loginForm'),
    signup: document.getElementById('signupForm')
};

function showForm(name) {
    if(!forms[name]) return;
    Object.keys(forms).forEach(k => { if(forms[k]) forms[k].classList.remove('active-form') });
    forms[name].classList.add('active-form');
}

// ── FORM NAVIGATION ────────────────────────────────
if (document.getElementById('signupText')) {
    document.getElementById('signupText').addEventListener('click', (e) => {
        e.preventDefault(); showForm('signup');
    });
}

if (document.getElementById('loginText')) {
    document.getElementById('loginText').addEventListener('click', (e) => {
        e.preventDefault(); showForm('login');
    });
}

// ── USER / ADMIN LOGIN ─────────────────────────────
document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form  = e.target;
    const errEl = document.getElementById('loginError');
    errEl.style.display = 'none';

    if (!form.checkValidity()) { form.classList.add('was-validated'); return; }

    const email    = document.getElementById('loginEmail').value.trim();
    const password = document.getElementById('loginPassword').value;

    try {
        // ── User login ──
        const res = await fetch(`${API}/users/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });
        const msg = await res.text();

        if (msg.startsWith('Login successful')) {
            const nameMatch = msg.match(/Welcome (.+?) \[/);
            const name = nameMatch ? nameMatch[1] : email;
            localStorage.setItem('userName', name);
            localStorage.setItem('userRole', 'USER');
            localStorage.setItem('userEmail', email);
            try {
                const users = await fetch(`${API}/users/search?name=${encodeURIComponent(name)}`).then(r => r.json());
                const user  = users.find(u => u.email === email);
                if (user) localStorage.setItem('userId', user.userId);
            } catch(_) {}
            window.location.href = 'movies.html';
        } else {
            errEl.textContent   = 'Invalid email or password.';
            errEl.style.display = 'block';
        }

    } catch (err) {
        errEl.textContent   = 'Could not connect to server. Make sure the app is running.';
        errEl.style.display = 'block';
    }
});

// ── USER SIGN UP ───────────────────────────────────
document.getElementById('signupForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const form  = e.target;
    const errEl = document.getElementById('signupError');
    const okEl  = document.getElementById('signupSuccess');
    errEl.style.display = 'none';
    okEl.style.display  = 'none';

    const password        = document.getElementById('signupPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (password !== confirmPassword) {
        document.getElementById('confirmPassword').setCustomValidity('mismatch');
        form.classList.add('was-validated');
        return;
    } else {
        document.getElementById('confirmPassword').setCustomValidity('');
    }

    if (!form.checkValidity()) { form.classList.add('was-validated'); return; }

    const firstName = document.getElementById('firstName').value.trim();
    const lastName  = document.getElementById('lastName').value.trim();
    const email     = document.getElementById('signupEmail').value.trim();
    
    let maxUserId = 0;
    try {
        const allUsers = await fetch(`${API}/users/all`).then(r => r.json());
        allUsers.forEach(u => {
            const num = parseInt(u.userId.replace('U', ''));
            if (!isNaN(num) && num > maxUserId) maxUserId = num;
        });
    } catch (e) { /* default to 0 */ }
    const userId = 'U' + (maxUserId + 1);

    try {
        const res = await fetch(`${API}/users/add`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userId, name: `${firstName} ${lastName}`, email, password, role: 'USER' })
        });
        const msg = await res.text();

        if (msg.includes('successfully')) {
            okEl.textContent   = 'Account created! You can now login.';
            okEl.style.display = 'block';
            form.reset();
            form.classList.remove('was-validated');
            setTimeout(() => showForm('login'), 1500);
        } else {
            errEl.textContent   = msg;
            errEl.style.display = 'block';
        }
    } catch (err) {
        errEl.textContent   = 'Could not connect to server.';
        errEl.style.display = 'block';
    }
});

