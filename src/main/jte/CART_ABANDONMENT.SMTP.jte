@param String fullName
@param String products
@param String cartLink

<!DOCTYPE html>
<html>
<head>
    <title>Your Cart is Waiting</title>
</head>
<body style="font-family: Arial, sans-serif; line-height: 1.6;">
    <h2>Your cart is waiting for you!</h2>
    <p>
        Hello ${fullName},
        <br>You left some items in your cart:
    </p>
    <p>${products}</p>
    <p>
        Complete your order now and don't miss out!
    </p>
    <p>
        <a href="${cartLink}" style="background-color: #FFC107; color: black; padding: 10px 20px; text-decoration: none; border-radius: 5px;">Complete My Order</a>
    </p>
    <p>
        Best regards,
        <br>The <strong>Me</strong> Team
    </p>
</body>
</html>
