const express = require('express');
const router = express.Router();
const { sendOtp, registerUser, verifyOtp, loginUser, resetPassword } = require('../controllers/authController');

// API gửi OTP
router.post('/send-otp', (req, res) => {
    const { email } = req.body;
    if (!email) {
        return res.status(400).json({ message: 'Email is required!' });
    }
    sendOtp(email, (error, message) => {
        if (error) {
            return res.status(500).json({ message: error });
        }
        return res.status(200).json({ message });
    });
});

// API đăng ký người dùng
router.post('/register', (req, res) => {
    const { username, password, email } = req.body;
    if (!username || !password || !email) {
        return res.status(400).json({ message: 'All fields are required!' });
    }
    registerUser(username, password, email, (error, message) => {
        if (error) {
            return res.status(500).json({ message: error });
        }
        return res.status(200).json({ message });
    });
});

// API xác thực OTP
router.post('/verify-otp', (req, res) => {
    const { email, otp } = req.body;
    if (!email || !otp) {
        return res.status(400).json({ message: 'Email and OTP are required!' });
    }
    verifyOtp(email, otp, (error, message) => {
        if (error) {
            return res.status(400).json({ message: error });
        }
        return res.status(200).json({ message });
    });
});

router.post('/login', (req, res) => {
    const { username, password } = req.body;
    if (!username || !password) {
        return res.status(400).json({ message: 'Username and password are required!' });
    }
    loginUser(username, password, (error, data) => {
        if (error) {
            return res.status(400).json({ message: error });
        }
        return res.status(200).json(data);
    });
});

// API Reset mật khẩu
router.post('/reset-password', (req, res) => {
    const { email, newPassword } = req.body;
    if (!email || !newPassword) {
        return res.status(400).json({ message: 'Email and New Password are required!' });
    }
    resetPassword(email, newPassword, (error, message) => {
        if (error) {
            return res.status(500).json({ message: error });
        }
        return res.status(200).json({ message });
    });
});

module.exports = router;
