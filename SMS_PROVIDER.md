# 📲 SMS Provider - Beem Africa

This project uses **Beem Africa** as the official SMS gateway provider for sending SMS notifications across the National Blood Transfusion Service Management System (NBT-SMS).

---

## ⚙️ SMS Integration Requirements

To successfully send SMS messages via Beem:

- You **must** apply for and obtain an approved **Sender Name** from Beem.
- You require valid API credentials provided by Beem.

---

## 📁 Required Environment Variables

Ensure the following variables are set in your `.env` file:

```env
SMS_USERNAME=
SMS_PASSWORD=
SMS_SOURCE=
SMS_URL=https://apisms.beem.africa/v1/send
```

## 🌐 Useful Links

- [Beem Africa Official Website](https://beem.africa/)
- [Beem SMS API Documentation](https://docs.beem.africa/)
