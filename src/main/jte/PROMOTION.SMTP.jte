@param String fullName
@param String percentage
@param String deadline
@param String promotionLink

<!DOCTYPE html>
<html>
<head>
    <title>Special Offer</title>
</head>
<body style="font-family: Arial, sans-serif; line-height: 1.6;">
    <h2>Enjoy ${percentage}% off your favorite items!</h2>
    <p>
        Hello ${fullName},
        <br>An exclusive offer awaits you: enjoy <strong>${percentage}%</strong> off our entire collection until <strong>${deadline}</strong>.
    </p>
    <p>
        <a href="${promotionLink}" style="background-color: #28A745; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">Shop the Offer</a>
    </p>
    <p>
        See you soon,
        <br>The <strong>Me</strong> Team
    </p>
</body>
</html>
