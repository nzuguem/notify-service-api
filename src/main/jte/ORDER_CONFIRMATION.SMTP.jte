@param String fullName
@param String orderNumber
@param String trackingLink

<!DOCTYPE html>
<html>
<head>
    <title>Order Confirmation</title>
</head>
<body style="font-family: Arial, sans-serif; line-height: 1.6;">
    <h2>Thank you for your order, ${fullName}!</h2>
    <p>
        We have successfully received your order <strong>${orderNumber}</strong>.
        It is now being processed and will be shipped shortly.
    </p>
    <p>
        You can track your order status by clicking the link below:
        <a href="${trackingLink}" style="color: #007BFF;">Track my order</a>
    </p>
    <p>
        Thank you for choosing us,
        <br>The <strong>Me</strong> Team
    </p>
</body>
</html>
</html>
