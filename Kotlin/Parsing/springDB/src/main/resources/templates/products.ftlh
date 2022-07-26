<!DOCTYPE html>
<html lang="ru">
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1>BUYSELL</h1>
<hr>
<h4>Товары со всей России</h4>
<form action="/" method="get">
    Поиск по названию объявления: <label>
        <input type="text" name="title">
    </label><br>
    <input type="submit" value="Поиск"/>
</form>
<#list productsNB as product>
    <div>
        <p><b>${product.id}</b> | <b>${product.title}</b> | <b>${product.price}</b> | <b>${product.oldPrice}</b> |
            <b>${product.link}</b> | <a href="/productNB/${product.id}">Подробнее...</a></p>
    </div>
<#else>
    <h3>Товаров нет</h3>
</#list>
<hr>
<h3>Создать новый товар</h3>
<form action="/productNB/create" method="post">
    Title: <label>
        <input type="text" name="title"/>
    </label><br><br>
    Price: <label>
        <input type="text" name="price"/>
    </label><br><br>
    oldPrice: <label>
        <input type="number" name="oldPrice"/>
    </label><br><br>
    Link: <label>
        <input type="text" name="link"/>
    </label><br><br>
    <input type="submit" value="Добавить товар"/>
</form>

<form action="/productNB/parse" method="post">
    <input type="submit" value="Start">
</form>
</body>
</html>