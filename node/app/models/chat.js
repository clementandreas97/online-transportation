var mongoose = require('mongoose');

module.exports = mongoose.model('Chat', {
    messageFrom: {
        type: String,
        default: ''
    },
    messageTo: {
        type: String,
        default: ''
    },
    message: {
        type: String,
        default: ''
    }
});