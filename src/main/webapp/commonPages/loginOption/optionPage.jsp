<!doctype html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>i-Dream</title>
		<script>
			$('.message a').click(function(){
				   $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
				});
		</script>
		<style>
		@import url(https://fonts.googleapis.com/css?family=Roboto:300);
			
			.login-page {
			  width: 360px;
			  padding: 8% 0 0;
			  margin: auto;
			}
			.form {
			  position: relative;
			  z-index: 1;
			  background: #FFFFFF;
			  max-width: 360px;
			  margin: 0 auto 100px;
			  padding: 45px;
			  text-align: center;
			  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
			}
			.form input {
			  font-family: "Roboto", sans-serif;
			  outline: 0;
			  background: #f2f2f2;
			  width: 100%;
			  border: 0;
			  margin: 0 0 15px;
			  padding: 15px;
			  box-sizing: border-box;
			  font-size: 14px;
			}
			.form button {
			  font-family: "Roboto", sans-serif;
			  text-transform: uppercase;
			  outline: 0;
			  background: #34495e;
			  width: 100%;
			  border: 0;
			  margin: 0 0 15px;
			  padding: 15px;
			  color: #FFFFFF;
			  font-size: 14px;
			  -webkit-transition: all 0.3 ease;
			  transition: all 0.3 ease;
			  cursor: pointer;
			}
			.form button:hover,.form button:active,.form button:focus {
			  background: #5b7691;
			}
			.form .message {
			  margin: 15px 0 0;
			  color: #b3b3b3;
			  font-size: 12px;
			}
			.form .message a {
			  color: #34495e;
			  text-decoration: none;
			}
			.form .register-form {
			  display: none;
			}
			.container {
			  position: relative;
			  z-index: 1;
			  max-width: 300px;
			  margin: 0 auto;
			}
			.container:before, .container:after {
			  content: "";
			  display: block;
			  clear: both;
			}
			.container .info {
			  margin: 50px auto;
			  text-align: center;
			}
			.container .info h1 {
			  margin: 0 0 15px;
			  padding: 0;
			  font-size: 36px;
			  font-weight: 300;
			  color: #1a1a1a;
			}
			.container .info span {
			  color: #4d4d4d;
			  font-size: 12px;
			}
			.container .info span a {
			  color: #000000;
			  text-decoration: none;
			}
			.container .info span .fa {
			  color: #EF3B3A;
			}
			body {
			  background: #34495e; /* fallback for old browsers */
			  background: -webkit-linear-gradient(right, #34495e, #34495e);
			  background: -moz-linear-gradient(right, #34495e, #34495e);
			  background: -o-linear-gradient(right, #34495e, #34495e);
			  background: linear-gradient(to left, #34495e, #34495e);
			  font-family: "Roboto", sans-serif;
			  -webkit-font-smoothing: antialiased;
			  -moz-osx-font-smoothing: grayscale;      
			}
		</style>
	</head>
	<body>
		<div class="login-page">
		  <div class="form">
		    <form class="login-form" action="loginOptionPreProcess.jsp?option=SuperAdmin" method="post" >
		      <button type="submit">Super Administrator Portal</button>
		      </form>
		       <form class="login-form" action="loginOptionPreProcess.jsp?option=Admin" method="post">
		      <button type="submit">Administrator Portal</button>
		    </form>
		    <form class="login-form" action="loginOptionPreProcess.jsp?option=Customer" method="post">
		      <button type="submit">Customer Portal</button>
		    </form>
		  </div>
		</div>
	</body>
</html>
