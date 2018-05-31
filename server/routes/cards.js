const express = require('express');
const router = express.Router();

/* GET cards listing. */
router.get('/', function (req, res, next) {
    var allowedOrigins = ['http://localhost', 'http://localhost:4200'];
    var origin = req.headers.origin;
    if (allowedOrigins.indexOf(origin) > -1) {
        res.set('Access-Control-Allow-Origin', origin);
    }
    var page = req.query.page ? Number(req.query.page) : 1;
    var pageSize = req.query.pageSize ? Number(req.query.pageSize) : 10;
    var searchValue = req.query.search;
    global.db.collection("cards")
        .find(
            {$text: {$search: searchValue}}
        )
        .project({ score: { $meta: "textScore" } })
        .sort({score: {$meta: "textScore"}})
        .skip((page - 1) * pageSize)
        .limit(pageSize)
        .toArray(function (err, result) { // 返回集合中所有数据
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
