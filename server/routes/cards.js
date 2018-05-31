const express = require('express');
const router = express.Router();

// TODO: 增加日志输出，记录关键事件
// 日志输出一般分为 4 个等级：
// - ERROR: 会直接影响用户的使用，需要立刻有人为介入修复的错误
// - WARN：可能会影响用户使用，但不需要立刻有人为介入的错误
// - INFO：用户关键事件，如接收到请求、返回响应
// - DEBUG：其他 debug 用的信息，通常用于开发期和 QA 期，生产环境部署时不会打印这种日志

// GET cards listing
router.get('/', function (req, res, next) {
    const allowedOrigins = ['http://localhost', 'http://localhost:4200'];
    const origin = req.headers.origin;
    if (allowedOrigins.indexOf(origin) > -1) {
        res.set('Access-Control-Allow-Origin', origin);
    }
    const page = req.query.page ? Number(req.query.page) : 1;
    const pageSize = req.query.pageSize ? Number(req.query.pageSize) : 10;
    const searchValue = req.query.search;

    // TODO 字符串统一使用单引号
    // TODO 使用依赖注入替换对 global 的使用
    global.db.collection("cards")
        .find(searchValue ? {$text: {$search: searchValue}} : {})
        .project({score: {$meta: "textScore"}})
        .sort({score: {$meta: "textScore"}})
        .skip((page - 1) * pageSize)
        .limit(pageSize)
        .toArray(function (err, result) { // 返回集合中所有数据
            if (err)
                throw err;
            console.log("成功连接cards");
            console.log(result);
            res.json(result);
        });
});

// TODO: exports 写在最前面
module.exports = router;
