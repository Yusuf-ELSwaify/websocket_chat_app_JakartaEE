<html>
<head>
    <link rel='stylesheet' type='text/css' media='screen' href='main.css'>
    <script src='script.js'></script>
</head>
<body onload="connect()">
<div id="container">
    <aside>
        <ul id="aside">

        </ul>
    </aside>
    <main>
        <ul id="chat">


        </ul>
        <footer>
            <form id="send_form">
                <textarea placeholder="Type your message" name="msg" id="msg"></textarea>
                <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/ico_picture.png" alt="">
                <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/1940306/ico_file.png" alt="">
                <button type="button" onclick="send()" id="send_button">Send</button>
            </form>

        </footer>
    </main>
</div>
</body>
</html>
