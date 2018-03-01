var Chat = require('./models/chat');
var User = require('./models/user');

// setting fcm
var admin = require("firebase-admin");
var serviceAccount = require("../config/serviceAccountKey.json");
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://pensidoto.firebaseio.com"
});

function getChat(res, from, to) {
    Chat.find({
        $or: [
            {$and: [
                { 'messageFrom' : from },
                { 'messageTo' : to}
            ]},
            {$and: [
                { 'messageFrom' : to },
                { 'messageTo' : from}
            ]}
        ]
    }, function(err, chats) {
        if (err) {
            res.send(err);
        }

        res.json(chats);
    });
}

module.exports = function (app) {

    // api ---------------------------------------------------------------------
    app.post('/api/users/updatetoken', function(req, res) {
        console.log(req.body);
        var query = {'username':req.body.username};
        var newData = {};
        newData.username = req.body.username;
        newData.userId = req.body.userId;
        newData.token = req.body.token;
        newData.finding = false;
        newData.hasOrder = false;
        User.findOneAndUpdate(query, newData, {upsert:true}, function(err, doc){
            if (err) return res.send(500, { error: err });
            return res.send("succesfully saved");
        });
    });

    app.post('/api/login', function(req, res) {
        return res.send("succesfully saved");
    });

    app.post('/api/chats/getconversation', function (req, res) {
        getChat(res, req.body.from, req.body.to);
    });

    app.post('/api/chats/addchat', function (req, res) {
        Chat.create({
            messageFrom: req.body.from,
            messageTo: req.body.to,
            message: req.body.message
        }, function(err, chat) {
            if (err) {
                res.send(err);
            }
            res.send('success');
        });
        // send fcm to receiver
        console.log(req.body.to);
        User.findOne({
            'username': req.body.to
        }, function(err, receiver) {
            var payload = {
                data: {
                    text: 'chat',
                    message: req.body.message
                }
            };
            admin.messaging().sendToDevice(receiver.token, payload)
                .then(function(response) {
                    // See the MessagingDevicesResponse reference documentation for
                    // the contents of response.
                    console.log("Successfully sent message:", response);
                })
                .catch(function(error) {
                    console.log("Error sending message:", error);
                });
        });
    });

    app.post('/api/users/findingorder', function(req, res) {
        var query = {'username':req.body.username};
        console.log(req.body.username);
        var newData = {};
        newData.finding = true;
        newData.hasOrder = false;
        User.findOneAndUpdate(query, newData, function(err, doc){
            if (err) return res.send(500, { error: err });
            return res.send("succesfully saved");
        });
    });

    app.post('/api/users/cancellingorder', function(req, res) {
        var query = {'username':req.body.username};
        var newData = {};
        newData.finding = false;
        newData.hasOrder = false;
        User.findOneAndUpdate(query, newData, function(err, doc){
            if (err) return res.send(500, { error: err });
            return res.send("succesfully saved");
        });
    });

    app.post('/api/users/startorder', function(req, res) {
        console.log(req.body);
        var query = {'userId':req.body.driverId};
        var newData = {};
        newData.finding = false;
        newData.hasOrder = true;
        User.findOneAndUpdate(query, newData, {upsert:true}, function(err, doc){
            console.log(1);
        });
        // send fcm to receiver
        User.findOne({
            'userId': req.body.driverId
        }, function(err, receiver) {
            var payload = {
                data: {
                    text: 'start',
                    username: req.body.username
                }
            };
            console.log("DEBUG:", receiver.token);
            admin.messaging().sendToDevice(receiver.token, payload)
                .then(function(response) {
                    // See the MessagingDevicesResponse reference documentation for
                    // the contents of response.
                    console.log("Successfully sent message:", response);
                    return res.send('success');
                })
                .catch(function(error) {
                    console.log("Error sending message:", error);
                });
        });
    })

    app.post('/api/users/finishorder', function(req, res) {
        var query = {'username':req.body.drivername};
        var newData = {};
        newData.finding = false;
        newData.hasOrder = false;
        User.findOneAndUpdate(query, newData, {upsert:true}, function(err, doc){
            if (err) return res.send(500, { error: err });
            return res.send("succesfully saved");
        });
        // send fcm to receiver
        User.findOne({
            'username': req.body.drivername
        }, function(err, receiver) {
            var payload = {
                data: {
                    text: 'finish'
                }
            };
            console.log("DEBUG:", receiver.token);
            admin.messaging().sendToDevice(receiver.token, payload)
                .then(function(response) {
                    // See the MessagingDevicesResponse reference documentation for
                    // the contents of response.
                    console.log("Successfully sent message:", response);
                })
                .catch(function(error) {
                    console.log("Error sending message:", error);
                });
        });
    });

    app.post('/api/users/getfinding', function(req, res) {
        console.log("test");
        User.find({
            $and : [
                { 'finding': true },
                { 'hasOrder': false}
            ]
        }, {'userId' :1, _id :0}, function(err, users) {
            if (err) {
                res.send(err);
            }

            res.json(users);
        });
    });
};