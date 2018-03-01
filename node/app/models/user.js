var mongoose = require('mongoose');

module.exports = mongoose.model('User', {
    username: {
        type: String,
        default: ''
    },
    userId: {
        type: String,
        default: ''
    },
    token: {
        type: String,
        default: ''
    },
    finding: {
        type: Boolean,
        default: false
    },
    hasOrder: {
        type: Boolean,
        default: false
    }
});