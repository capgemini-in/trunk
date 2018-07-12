<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@include file="include.jsp"%>

<!DOCTYPE html>
<html>
<head>
<title>Capgemini</title>

<!-- for-mobile-apps -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Payment Form Widget Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<!-- //for-mobile-apps -->
<style>
.myMain {
  background-image:<img src="D:/Java user Mangment Workspace/pocwebapp/src/main/webapp/static/images/gionee.gif" alt="Mountain View">
	/* background-image: url("<c:url value='D:/Java user Mangment Workspace/pocwebapp/src/main/webapp/static/images/gionee.gif'/>"); */
	min-height: 100%;
	height: 500px;
	background-position: center;
	background-size: cover;
	opacity: 0.03;
	filter: alpha(opacity = 60);
}

#myForm {
	opacity: 1.0;
}
</style>
<link href="static/css/style.css" rel="stylesheet" type="text/css" media="all" />
<link href='//fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic' rel='stylesheet' type='text/css'>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="https://www.paypalobjects.com/api/checkout.js"></script>
<script>
paypal.Button.render({
  // Configure environment
  env: 'sandbox',
  client: {
    sandbox: 'demo_sandbox_client_id',
    production: 'demo_production_client_id'
  },
  // Customize button (optional)
  locale: 'en_US',
  style: {
    size: 'small',
    color: 'gold',
    shape: 'pill',
  },
  // Set up a payment
   payment: function (data, actions) {
  return actions.payment.create({
    transactions: [{
      amount: {
        total: '30.00',
        currency: 'USD',
        details: {
          subtotal: '30.00',
          tax: '0.00',
          shipping: '0.00',
          handling_fee: '0.00',
          shipping_discount: '0.00',
          insurance: '0.00'
        }
      },
      description: 'The payment transaction description.',
      custom: '90048630024435',
      //invoice_number: '12345', Insert a unique invoice number
      payment_options: {
        allowed_payment_method: 'INSTANT_FUNDING_SOURCE'
      },
      soft_descriptor: 'ECHI5786786',
      item_list: {
        items: [
          {
            name: 'Car Tyre',
            description: 'CEAT car tyre.',
            quantity: '6',
            price: '3',
            tax: '0.00',
            sku: '1',
            currency: 'USD'
          },
          {
            name: 'Gear Box',
            description: 'A Complete Gear Box.',
            quantity: '1',
            price: '12',
            tax: '0.00',
            sku: 'product34',
            currency: 'USD'
          }
        ],
        shipping_address: {
          recipient_name: 'Divyam Vyas',
          line1: '4th Floor',
          line2: 'Block 2',
          city: 'San Jose',
          country_code: 'US',
          postal_code: '95131',
          phone: '011862212345678',
          state: 'CA'
        }
      }
    }],
    note_to_payer: 'Contact us for any questions on your order.'
  });
},
  // Execute the payment
  onAuthorize: function (data, actions) {
    return actions.payment.execute()
      .then(function () {
        // Show a confirmation message to the buyer
        window.alert('Thank you for your purchase!');
      });
  }
}, '#paypal-button');
</script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body class="w3-light-grey w3-content" style="max-width: 1600px"
	ng-app="myApp" ng-Controller="myCtrl">
<%@include file="nav.jsp"%>
	<!-- Overlay effect when opening sidenav on small screens -->
	<div class="w3-overlay w3-hide-large w3-animate-opacity"
		onclick="w3_close()" style="cursor: pointer" title="close side menu"
		id="myOverlay"></div>
	<div class="w3-main w3-border" style="margin-left: 340px; margin-right: 40px">
		<!-- Header -->
		<%@include file="Header.jsp"%>
	
		
	
		
			
			<script src="static/js/easyResponsiveTabs.js" type="text/javascript"></script>
					<script type="text/javascript">
						$(document).ready(function () {
							$('#horizontalTab').easyResponsiveTabs({
								type: 'default', //Types: default, vertical, accordion           
								width: 'auto', //auto or any width like 600px
								fit: true   // 100% fit in a container
							});
						});
						
					</script>
						<div class="sap_tabs">
							<div id="horizontalTab" style="display: block; width: 100%; margin: 0px;">
								<div class="pay-tabs">
									<h2>Select Gateway Options</h2>
									  <ul class="resp-tabs-list">
										  <li class="resp-tab-item" aria-controls="tab_item-0" role="tab"><span><label class="pic1"></label>SMS</span></li>
										  <li class="resp-tab-item" aria-controls="tab_item-1" role="tab"><span><label class="pic1"></label>Email</span></li>
										  <li class="resp-tab-item" aria-controls="tab_item-2" role="tab"><span><label class="pic1"></label>Payment</span></li> 
										  <div class="clear"></div>
									  </ul>	
								</div>
								<div class="resp-tabs-container">
									<div class="tab-1 resp-tab-content" aria-labelledby="tab_item-0">
										<div class="payment-info">
											<h3>SMS</h3>
											<div class="login-tab">
												<div class="user-login">													
													<form>
														<input type="text" value="9999999999" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = '9999999999';}" required="">
														<textarea class="user-login" rows="5" cols="67" placeholder="Type your Message"></textarea>
															<div class="user-grids">
																<div class="user-right">
																	<input type="submit" value="Submit">
																</div>
																<div class="clear"></div>
															</div>
													</form>
												</div>
											</div>
										</div>
									</div>
									<div class="tab-1 resp-tab-content" aria-labelledby="tab_item-1">
										<div class="payment-info">
											<h3>Email</h3>
											<div class="login-tab">
												<div class="user-login">													
													<form>
														<input type="text" value="To" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'TO';}" required="">
														<input type="text" value="CC" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'CC';}" required="">
														<input type="text" value="Subject" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Subject';}" required="">

														<textarea class="user-login" rows="5" cols="67" placeholder="Type your Message"></textarea>
															<div class="user-grids">
																<div class="user-right">
																	<input type="submit" value="Submit">
																</div>
																<div class="clear"></div>
															</div>
													</form>
												</div>
											</div>
										</div>
									</div>
									<div class="tab-1 resp-tab-content" aria-labelledby="tab_item-2">								
										  <h4 class="d-flex justify-content-between align-items-center mb-3">
											<span class="text-muted">Your cart</span>
											<span class="badge badge-secondary badge-pill">2</span>
										  </h4>
										  <ul class="list-group mb-3">
											<li class="list-group-item d-flex justify-content-between lh-condensed">
											  <div>
												<h6 class="my-0">Car Tyre</h6>
												<small class="text-muted">Brief description</small>
											  </div>
											  <span class="text-muted">$18</span>
											</li>
											<li class="list-group-item d-flex justify-content-between lh-condensed">
											  <div>
												<h6 class="my-0">Gear Box</h6>
												<small class="text-muted">Brief description</small>
											  </div>
											  <span class="text-muted">$12</span>
											</li>
											<li class="list-group-item d-flex justify-content-between bg-light">
											  <div class="text-success">
												<h6 class="my-0">Promo code</h6>
												<small>EXAMPLECODE</small>
											  </div>
											  <span class="text-success">-$5</span>
											</li>
											<li class="list-group-item d-flex justify-content-between">
											  <span>Total (USD)</span>
											  <strong>$30</strong>
											</li>
										  </ul>
										<div class="payment-info">
											<div class="login-tab">
												<center><div class="user-login" id="paypal-button"></div></center>
											</div>
										</div>
									</div>
								</div>						
							</div>
						</div>	


	
	</div>
</body>
</html>
</html>