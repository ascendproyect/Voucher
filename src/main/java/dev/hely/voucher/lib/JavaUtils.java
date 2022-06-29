package dev.hely.voucher.lib;

//============================================================
// This file was created by DevDipin!
// Hely Development Developer, and Management Team Member.
//
// If any questions, please contact on discord.
//
// File Information:
//   Path: dev.hely.crates.lib
//   Date: Monday, June 13, 2022 - 4:08 PM
//
// Contact Information:
//   Discord: Andrew!!#4468
//   Hely Development Discord: https://discord.gg/J9XMt8FCxN
//============================================================

public class JavaUtils {
    public static boolean isInt(String sInt) {
        try {
            Integer.parseInt(sInt);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
