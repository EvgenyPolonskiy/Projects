<!DOCTYPE html>
<html lang="ru">
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1>BUYSELL</h1>
<hr>
<h4>Подробная информация о товаре</h4>
<b>Id: </b>${productNB.id}<br>
<b>title: </b>${productNB.title}<br>
<b>price: </b>${productNB.price}<br>
<b>oldPrice: </b>${productNB.oldPrice}<br>
<b>link: </b>${productNB.link}<br>
<hr>
<form action="/productNB/delete/${productNB.id}" method="post">
    <input type="submit" value="Удалить товар"/>
</form>
</body>
</html>
