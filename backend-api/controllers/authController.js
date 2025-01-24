const nodemailer = require('nodemailer');
const db = require('../config/db');

// Gửi OTP qua email
const sendOtp = (email, callback) => {
    const otp = Math.floor(100000 + Math.random() * 900000); // OTP 6 chữ số
    const transporter = nodemailer.createTransport({
        service: 'gmail',
        auth: {
            user: process.env.MAIL_USER,
            pass: process.env.MAIL_PASS
        }
    });

    const mailOptions = {
        from: process.env.MAIL_USER,
        to: email,
        subject: 'OTP for Account Registration',
        text: `Your OTP is ${otp}.`
    };

    transporter.sendMail(mailOptions, (error, info) => {
        if (error) {
            return callback(error, null);
        } else {
            // Lưu OTP vào database tạm thời để xác thực
            const query = 'INSERT INTO otp_verification (email, otp) VALUES (?, ?)';
            db.query(query, [email, otp], (err, result) => {
                if (err) {
                    return callback(err, null);
                }
                callback(null, 'OTP sent to your email!');
            });
        }
    });
};

// Đăng ký tài khoản
const registerUser = (username, password, email, callback) => {
    const query = 'INSERT INTO users (username, password, email) VALUES (?, ?, ?)';
    db.query(query, [username, password, email], (err, result) => {
        if (err) {
            return callback(err, null);
        }
        callback(null, 'User registered successfully!');
    });
};

// Xác thực OTP
const verifyOtp = (email, otp, callback) => {
    const query = 'SELECT * FROM otp_verification WHERE email = ? ORDER BY id DESC LIMIT 1';
    db.query(query, [email], (err, results) => {
        if (err) {
            return callback(err, null);
        }
        if (results.length > 0 && results[0].otp === otp) {
            callback(null, 'OTP verified successfully!');
        } else {
            callback('Invalid OTP', null);
        }
    });
};

// Đăng nhập người dùng bằng username
const loginUser = (username, password, callback) => {
    const query = 'SELECT * FROM users WHERE username = ?';
    db.query(query, [username], (err, results) => {
        if (err) {
            return callback(err, null);
        }
        if (results.length === 0) {
            return callback('User not found', null);
        }
        const user = results[0];
        
        // Kiểm tra mật khẩu
        if (password === user.password) { // Nếu không mã hóa
        // Nếu mã hóa mật khẩu, sử dụng:
        // bcrypt.compare(password, user.password, (err, isMatch) => {
            return callback(null, {
                message: 'Login successful!',
                user: { id: user.id, username: user.username, email: user.email }
            });
        } else {
            return callback('Invalid password', null);
        }
    });
};

// Reset mật khẩu


module.exports = { sendOtp, registerUser, verifyOtp, loginUser, resetPassword };

