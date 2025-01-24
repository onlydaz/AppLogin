// Ví dụ về mô hình người dùng
const db = require('../config/db');

// Đăng ký người dùng
const createUser = (username, password, email, callback) => {
    const query = 'INSERT INTO users (username, password, email) VALUES (?, ?, ?)';
    db.query(query, [username, password, email], (err, result) => {
        if (err) {
            return callback(err, null);
        }
        callback(null, result);
    });
};

module.exports = { createUser };
