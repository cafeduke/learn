function toPascalCase(str) {
    if (typeof str !== 'string')
        return "";
    var result = "";
    str.split(/\s+/).forEach(function (token, index) {
        result = result + token.charAt(0).toUpperCase() + token.substring(1);
    });
    return result;
}
function toCamelCase(str) {
    if (typeof str !== 'string')
        return "";
    var result = "";
    str.split(/\s+/).forEach(function (token, index) {
        result = result
            + ((index == 0) ? token.charAt(0).toLowerCase() : token.charAt(0).toUpperCase())
            + token.substring(1);
    });
    return result;
}
function h1(str) {
    console.log();
    console.log("--------------------------------------------------------------------------------");
    console.log(str);
    console.log("--------------------------------------------------------------------------------");
}
var str = ["Hello World", "HelloWorld", "helloWorld", "This is it NoW There MiXeD", "hello world"];
h1("Pascal Case");
for (var _i = 0, str_1 = str; _i < str_1.length; _i++) {
    var curr = str_1[_i];
    console.log(curr + "-->" + toPascalCase(curr));
}
h1("Camel Case");
for (var _a = 0, str_2 = str; _a < str_2.length; _a++) {
    var curr = str_2[_a];
    console.log(curr + "-->" + toCamelCase(curr));
}
