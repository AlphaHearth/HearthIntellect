const express = require('express');
const router = express.Router();

/* GET cards listing. */
router.get('/', function (req, res, next) {
    const allowedOrigins = ['http://localhost', 'http://localhost:4200'];
    const origin = req.headers.origin;
    if (allowedOrigins.indexOf(origin) > -1) {
        res.set('Access-Control-Allow-Origin', origin);
    }
    const page = req.query.page ? Number(req.query.page) : 1;
    const pageSize = req.query.pageSize ? Number(req.query.pageSize) : 10;
    const searchValue = req.query.search;
    global.db.collection("cards")
        .find(
            searchValue ? {$text: {$search: searchValue}} : {}
        )
        .project({score: {$meta: "textScore"}})
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

module.exports = router;
