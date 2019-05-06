<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

    <!-- Static content -->
    <style>
        .form {
            background-color: #efefef;
            width: 600px;
            height: 250px;
            border-radius: 7px;
            padding: 20px;
        }
    </style>
    <script type="text/javascript">
        function validate() {
            var text = document.getElementById("text").value;
            if (text === '') {
                alert('Please enter any text');
                return false;
            } else {
                return true;
            }
        }
    </script>

    <title>Language Application</title>
</head>
<body>
<h1>Language Application </h1>
<hr>

<div class="form">
    <form action="match" method="post" onsubmit="return validate()">
        <div>
            <div>Enter your text</div>
            <div><textarea id="text" name="text" rows="15" cols="50"></textarea></div>
            <div><input type="submit" value="Submit"></div>
        </div>
    </form>
</div>

</body>
</html>