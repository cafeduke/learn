const express = require("express");
const redis = require("redis")

const app = express();
const cache = redis.createClient({
    host: "redis-server",
    port: 6379
});
cache.set("count.visit", 0);

app.get("/", (request, response) => {

    cache.get("count.visit", (err, count) => {
        response.send("Number of visits :" + count);
        cache.set("count.visit", parseInt(count) + 1)
    });
});

app.listen(8080, () => {
    console.log("Listening at port 8080");
});
