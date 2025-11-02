function toPascalCase (str:string): string 
{
  if (typeof str !== 'string')
    return "";

  let result = "";  
  str.split(/\s+/).forEach((token: string, index: number) => 
  {
    result = result + token.charAt(0).toUpperCase() + token.substring(1);
  });
  return result;
}

function toCamelCase (str:string): string 
{
  if (typeof str !== 'string')
    return "";

  let result = "";
  str.split(/\s+/).forEach((token: string, index: number) =>
  {
    result = result
      + ((index == 0) ? token.charAt(0).toLowerCase() : token.charAt(0).toUpperCase())
      + token.substring(1);
  });
  return result;
}

function h1 (str: string): void
{
  console.log();
  console.log("--------------------------------------------------------------------------------");
  console.log(str);
  console.log("--------------------------------------------------------------------------------");
}

let str: string[] = ["Hello World", "HelloWorld", "helloWorld", "This is it NoW There MiXeD", "hello world"];

h1("Pascal Case")
for (var curr of str)
  console.log(curr + "-->" + toPascalCase(curr));

h1("Camel Case")
for (var curr of str)
  console.log(curr + "-->" + toCamelCase(curr));

