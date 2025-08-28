Steganography
Project Title
SecretFrame : Image-based Steganography with Normal and QR Code Encryption

Abstract
This project implements a secure steganography system that allows users to hide messages inside images. Messages can be embedded and retrieved in two ways: normal encoding/decoding and QR-based encoding/decoding. In the QR-based mode, the message is first encrypted, converted into a QR code, and then embedded into the image. Users can decode messages either directly from the image. This dual approach ensures secure, flexible, and user-friendly message hiding.

Features
Normal Message Encoding/Decoding: Embed messages directly into images and retrieve them.
QR Code Encoding/Decoding: Convert encrypted messages into QR codes before embedding into images.
Message Encryption: Ensures confidentiality of hidden messages.
Image Steganography: Hides messages or QR codes in images without noticeable visual distortion.
User-friendly UI: Simple interface for both encoding and decoding processes.

Technology Used
Java – Core language for project logic and GUI.
VS Code – IDE for development and debugging.

Libraries:
ZXing – QR code generation and scanning
javax.crypto – Message encryption/decryption
javax.imageio – Image processing

Advantages:
Secure and encrypted message hiding
Dual encoding options: normal and QR-based
Cross-platform solution using Java
User-friendly interface


Encode a message as a QR code into an image, then decode it either from the image directl
