var express = require('express');
var router = express.Router();

/* GET cards listing. */
router.get('/', function (req, res, next) {
    var allowedOrigins = ['http://locaohost', 'http://localhost:4200'];
    var origin = req.headers.origin;
    if (allowedOrigins.indexOf(origin) > -1) {
        res.set('Access-Control-Allow-Origin', origin);
    }
    global.db.collection("cards").find({name: "Crowd Favorite"}).toArray(function (err, result) { // 返回集合中所有数据
        if (err) throw err;
        console.log("成功连接cards");
        console.log(result);
        res.json(result);
    });
});

/* GET users listing. */
router.get('/1', function (req, res, next) {
    // res.send('respond with a resource');
    res.json({user: 1});
});

module.exports = router;
