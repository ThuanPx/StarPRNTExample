package com.example.demostarprnt;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.ICommandBuilder.AlignmentPosition;
import com.starmicronics.starioextension.ICommandBuilder.BarcodeSymbology;
import com.starmicronics.starioextension.ICommandBuilder.BarcodeWidth;
import com.starmicronics.starioextension.ICommandBuilder.CodePageType;
import com.starmicronics.starioextension.ICommandBuilder.InternationalType;
import com.starmicronics.starioextension.StarIoExt.CharacterCode;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class JapaneseReceiptsImpl extends ILocalizeReceipts {

    public JapaneseReceiptsImpl() {
        mLanguageCode = "Ja";

        mCharacterCode = CharacterCode.Japanese;
    }

    @Override
    public void appendSelfOrderReceiptData(ICommandBuilder builder, boolean utf8, Resources resources, Bitmap bitmap) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            try {
                encoding = Charset.forName("Shift-JIS");

                builder.appendCodePage(CodePageType.CP932);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendInternational(InternationalType.Japan);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        Bitmap logo = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher);

        builder.appendBitmap(logo, true);

        builder.appendEmphasis(true);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                        "Neolab - VN\n" +
                        "\n"+
                        "TEL(03)-1234-5678\n").getBytes(encoding));

        builder.appendEmphasis(false);

        // total 48
        String totalLength = "                                               ";
        // text = totalLength - ten menu - price
        String space = totalLength.substring(5);
//        String finalText =  nameMenu + space + price

        builder.append((
                        "344 Duong 2/9 \n" +
                        "\n" +
                        "July 31,2019 at 19:09 \n" +
                        "\n"+
                        "Order-a                                  ¥99.999\n").getBytes(encoding));

        builder.appendEmphasis(true);

        builder.append((
                        "subtotal                                      ¥0\n"+
                        "tax                                           ¥0\n"+
                        "Billing                                  ¥99.999\n"+
                        "\n").getBytes(encoding));

        builder.appendEmphasis(false);

        builder.append((
                        "Card-No                         *****-*****-0109\n"+
                        "Order-No                               123456789\n").getBytes(encoding));


        builder.appendAlignment(AlignmentPosition.Center);

        Bitmap qrCode = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher);

        builder.appendBitmap(bitmap, false);

    }

    @Override
    public Bitmap createSelfOrderReceiptImage() {
        String textToPrint =
                "　　　　　　　　　　　　　　　スター電機\n" +
                        "　　　　　　　　　　　　　修理報告書　兼領収書\n" +
                        "---------------------------------------------------------\n" +
                        "発行日時：YYYY年MM月DD日HH時MM分\n" +
                        "TEL：054-347-XXXX\n" +
                        "\n" +
                        "　　　　　ｲｹﾆｼ  ｼｽﾞｺ  ｻﾏ\n" +
                        "　お名前：池西　静子　様\n" +
                        "　御住所：静岡市清水区七ツ新屋\n" +
                        "　　　　　５３６番地\n" +
                        "　伝票番号：No.12345-67890\n" +
                        "\n" +
                        "　この度は修理をご用命頂き有難うございます。\n" +
                        " 今後も故障など発生した場合はお気軽にご連絡ください。\n" +
                        "\n" +
                        "品名／型名　　　　　　　　　数量　　　　　　金額　　　　　　備考\n" +
                        "---------------------------------------------------------\n" +
                        "制御基板　　　　　　　　　　　１　　　　１０，０００　　　　配達\n" +
                        "操作スイッチ　　　　　　　　　１　　　　　３，８００　　　　配達\n" +
                        "パネル　　　　　　　　　　　　１　　　　　２，０００　　　　配達\n" +
                        "技術料　　　　　　　　　　　　１　　　　１５，０００\n" +
                        "出張費用　　　　　　　　　　　１　　　　　５，０００\n" +
                        "---------------------------------------------------------\n" +
                        "\n" +
                        "　　　　　　　　　　　　　　　　　　　　　　小計　¥ ３５，８００\n" +
                        "　　　　　　　　　　　　　　　　　　　　　　内税　¥ 　１，７９０\n" +
                        "　　　　　　　　　　　　　　　　　　　　　　合計　¥ ３７，５９０\n" +
                        "\n" +
                        "　お問合わせ番号　　12345-67890\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, Constants.PAPER_SIZE_FOUR_INCH, typeface);
    }

    @Override
    public void append2inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            try {
                encoding = Charset.forName("Shift-JIS");

                builder.appendCodePage(CodePageType.CP932);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendInternational(InternationalType.Japan);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendEmphasis(true);

        builder.appendMultipleHeight("スター電機\n".getBytes(encoding), 3);

        builder.appendMultipleHeight("修理報告書　兼領収書\n".getBytes(encoding), 2);

        builder.appendEmphasis(false);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "--------------------------------\n" +
                        "発行日時：YYYY年MM月DD日HH時MM分\n" +
                        "TEL：054-347-XXXX\n" +
                        "\n" +
                        "         ｲｹﾆｼ  ｼｽﾞｺ   ｻﾏ\n" +
                        "お名前：池西　静子　様\n" +
                        "御住所：静岡市清水区七ツ新屋\n" +
                        "　　　　５３６番地\n" +
                        "伝票番号：No.12345-67890\n" +
                        "\n" +
                        "　この度は修理をご用命頂き有難うございます。\n" +
                        " 今後も故障など発生した場合はお気軽にご連絡ください。\n" +
                        "\n" +
                        "品名／型名　数量　金額　備考\n" +
                        "--------------------------------\n" +
                        "制御基板　　   1 10,000  配達\n" +
                        "操作スイッチ   1  3,800  配達\n" +
                        "パネル　　　   1  2,000  配達\n" +
                        "技術料　　　   1 15,000\n" +
                        "出張費用　　   1  5,000\n" +
                        "--------------------------------\n" +
                        "\n" +
                        "             小計      \\ 35,800\n" +
                        "             内税      \\  1,790\n" +
                        "             合計      \\ 37,590\n" +
                        "\n" +
                        "　お問合わせ番号　12345-67890\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public void append3inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            try {
                encoding = Charset.forName("Shift-JIS");

                builder.appendCodePage(CodePageType.CP932);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendInternational(InternationalType.Japan);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendEmphasis(true);

        builder.appendMultipleHeight("スター電機\n".getBytes(encoding), 3);

        builder.appendMultipleHeight("修理報告書　兼領収書\n".getBytes(encoding), 2);

        builder.appendEmphasis(false);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------------------\n" +
                        "発行日時：YYYY年MM月DD日HH時MM分\n" +
                        "TEL：054-347-XXXX\n" +
                        "\n" +
                        "           ｲｹﾆｼ  ｼｽﾞｺ   ｻﾏ\n" +
                        "　お名前：池西　静子　様\n" +
                        "　御住所：静岡市清水区七ツ新屋\n" +
                        "　　　　　５３６番地\n" +
                        "　伝票番号：No.12345-67890\n" +
                        "\n" +
                        "　この度は修理をご用命頂き有難うございます。\n" +
                        " 今後も故障など発生した場合はお気軽にご連絡ください。\n" +
                        "\n" +
                        "品名／型名　          数量      金額　   備考\n" +
                        "------------------------------------------------\n" +
                        "制御基板　          　  1      10,000     配達\n" +
                        "操作スイッチ            1       3,800     配達\n" +
                        "パネル　　          　  1       2,000     配達\n" +
                        "技術料　          　　  1      15,000\n" +
                        "出張費用　　            1       5,000\n" +
                        "------------------------------------------------\n" +
                        "\n" +
                        "                            小計       \\ 35,800\n" +
                        "                            内税       \\  1,790\n" +
                        "                            合計       \\ 37,590\n" +
                        "\n" +
                        "　お問合わせ番号　　12345-67890\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public void append4inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            try {
                encoding = Charset.forName("Shift-JIS");

                builder.appendCodePage(CodePageType.CP932);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendInternational(InternationalType.Japan);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendEmphasis(true);

        builder.appendMultipleHeight("スター電機\n".getBytes(encoding), 3);

        builder.appendMultipleHeight("修理報告書　兼領収書\n".getBytes(encoding), 2);

        builder.appendEmphasis(false);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "---------------------------------------------------------------------\n" +
                        "発行日時：YYYY年MM月DD日HH時MM分\n" +
                        "TEL：054-347-XXXX\n" +
                        "\n" +
                        "           ｲｹﾆｼ  ｼｽﾞｺ   ｻﾏ\n" +
                        "　お名前：池西　静子　様\n" +
                        "　御住所：静岡市清水区七ツ新屋\n" +
                        "　　　　　５３６番地\n" +
                        "　伝票番号：No.12345-67890\n" +
                        "\n" +
                        "この度は修理をご用命頂き有難うございます。\n" +
                        " 今後も故障など発生した場合はお気軽にご連絡ください。\n" +
                        "\n" +
                        "品名／型名　                 数量             金額　          備考\n" +
                        "---------------------------------------------------------------------\n" +
                        "制御基板　　                   1             10,000            配達\n" +
                        "操作スイッチ                   1              3,800            配達\n" +
                        "パネル　　　                   1              2,000            配達\n" +
                        "技術料　　　                   1             15,000\n" +
                        "出張費用　　                   1              5,000\n" +
                        "---------------------------------------------------------------------\n" +
                        "\n" +
                        "                                                 小計       \\ 35,800\n" +
                        "                                                 内税       \\  1,790\n" +
                        "                                                 合計       \\ 37,590\n" +
                        "\n" +
                        "　お問合わせ番号　　12345-67890\n" +
                        "\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public Bitmap create2inchRasterReceiptImage() {
        String textToPrint =
                "　　　　　　スター電機\n" +
                        "　　　　修理報告書　兼領収書\n" +
                        "----------------------------\n" +
                        "発行日時：YYYY年MM月DD日HH時MM分\n" +
                        "TEL：054-347-XXXX\n" +
                        "\n" +
                        "　　　　　ｲｹﾆｼ  ｼｽﾞｺ  ｻﾏ\n" +
                        "　お名前：池西　静子　様\n" +
                        "　御住所：静岡市清水区七ツ新屋\n" +
                        "　　　　　５３６番地\n" +
                        "　伝票番号：No.12345-67890\n" +
                        "\n" +
                        "　この度は修理をご用命頂き有難うございます。\n" +
                        " 今後も故障など発生した場合はお気軽にご連絡ください。\n" +
                        "\n" +
                        "品名／型名　 数量　　金額\n" +
                        "----------------------------\n" +
                        "制御基板　　　１　　１０，０００\n" +
                        "操作スイッチ　１　　　３，０００\n" +
                        "パネル　　　　１　　　２，０００\n" +
                        "技術料　　　　１　　１５，０００\n" +
                        "出張費用　　　１　　　５，０００\n" +
                        "----------------------------\n" +
                        "\n" +
                        "　　　　　　小計　¥ ３５，８００\n" +
                        "　　　　　　内税　¥ 　１，７９０\n" +
                        "　　　　　　合計　¥ ３７，５９０\n" +
                        "\n" +
                        "　お問合わせ番号　　12345-67890\n";

        int      textSize = 22;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, Constants.PAPER_SIZE_TWO_INCH, typeface);
    }

    @Override
    public Bitmap create3inchRasterReceiptImage() {
        String textToPrint =
                "　　　　　　　　　　スター電機\n" +
                        "　　　　　　　　修理報告書　兼領収書\n" +
                        "---------------------------------------\n" +
                        "発行日時：YYYY年MM月DD日HH時MM分\n" +
                        "TEL：054-347-XXXX\n" +
                        "\n" +
                        "　　　　　ｲｹﾆｼ  ｼｽﾞｺ  ｻﾏ\n" +
                        "　お名前：池西　静子　様\n" +
                        "　御住所：静岡市清水区七ツ新屋\n" +
                        "　　　　　５３６番地\n" +
                        "　伝票番号：No.12345-67890\n" +
                        "\n" +
                        "　この度は修理をご用命頂き有難うございます。\n" +
                        " 今後も故障など発生した場合はお気軽にご連絡ください。\n" +
                        "\n" +
                        "品名／型名　　　　数量　　　金額　　　　備考\n" +
                        "---------------------------------------\n" +
                        "制御基板　　　　　　１　１０，０００　　配達\n" +
                        "操作スイッチ　　　　１　　３，８００　　配達\n" +
                        "パネル　　　　　　　１　　２，０００　　配達\n" +
                        "技術料　　　　　　　１　１５，０００\n" +
                        "出張費用　　　　　　１　　５，０００\n" +
                        "---------------------------------------\n" +
                        "\n" +
                        "　　　　　　　　　　　　小計　¥ ３５，８００\n" +
                        "　　　　　　　　　　　　内税　¥ 　１，７９０\n" +
                        "　　　　　　　　　　　　合計　¥ ３７，５９０\n" +
                        "\n" +
                        "　お問合わせ番号　　12345-67890\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, Constants.PAPER_SIZE_THREE_INCH, typeface);
    }

    @Override
    public Bitmap create4inchRasterReceiptImage() {
        String textToPrint =
                "　　　　　　　　　　　　　　　スター電機\n" +
                        "　　　　　　　　　　　　　修理報告書　兼領収書\n" +
                        "---------------------------------------------------------\n" +
                        "発行日時：YYYY年MM月DD日HH時MM分\n" +
                        "TEL：054-347-XXXX\n" +
                        "\n" +
                        "　　　　　ｲｹﾆｼ  ｼｽﾞｺ  ｻﾏ\n" +
                        "　お名前：池西　静子　様\n" +
                        "　御住所：静岡市清水区七ツ新屋\n" +
                        "　　　　　５３６番地\n" +
                        "　伝票番号：No.12345-67890\n" +
                        "\n" +
                        "　この度は修理をご用命頂き有難うございます。\n" +
                        " 今後も故障など発生した場合はお気軽にご連絡ください。\n" +
                        "\n" +
                        "品名／型名　　　　　　　　　数量　　　　　　金額　　　　　　備考\n" +
                        "---------------------------------------------------------\n" +
                        "制御基板　　　　　　　　　　　１　　　　１０，０００　　　　配達\n" +
                        "操作スイッチ　　　　　　　　　１　　　　　３，８００　　　　配達\n" +
                        "パネル　　　　　　　　　　　　１　　　　　２，０００　　　　配達\n" +
                        "技術料　　　　　　　　　　　　１　　　　１５，０００\n" +
                        "出張費用　　　　　　　　　　　１　　　　　５，０００\n" +
                        "---------------------------------------------------------\n" +
                        "\n" +
                        "　　　　　　　　　　　　　　　　　　　　　　小計　¥ ３５，８００\n" +
                        "　　　　　　　　　　　　　　　　　　　　　　内税　¥ 　１，７９０\n" +
                        "　　　　　　　　　　　　　　　　　　　　　　合計　¥ ３７，５９０\n" +
                        "\n" +
                        "　お問合わせ番号　　12345-67890\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, Constants.PAPER_SIZE_FOUR_INCH, typeface);
    }

    @Override
    public Bitmap createCouponImage(Resources resources) {
        return BitmapFactory.decodeResource(resources, R.drawable.ic_launcher);
    }

    @Override
    public Bitmap createEscPos3inchRasterReceiptImage() {
        String textToPrint =
                "\n" +
                "　　　　　　　 スター電機\n" +
                        "　　　　　 修理報告書　兼領収書\n" +
                        "-----------------------------------\n" +
                        "発行日時：YYYY年MM月DD日HH時MM分\n" +
                        "TEL：054-347-XXXX\n" +
                        "\n" +
                        "　　　　　ｲｹﾆｼ  ｼｽﾞｺ  ｻﾏ\n" +
                        "　お名前：池西　静子　様\n" +
                        "　御住所：静岡市清水区七ツ新屋\n" +
                        "　　　　　５３６番地\n" +
                        "　伝票番号：No.12345-67890\n" +
                        "\n" +
                        "　この度は修理をご用命頂き有難うございます。\n" +
                        " 今後も故障など発生した場合はお気軽にご連絡ください。\n" +
                        "\n" +
                        "品名／型名　　　数量　　　金額　　　　備考\n" +
                        "-----------------------------------\n" +
                        "制御基板　　     １　１０，０００　　配達\n" +
                        "操作スイッチ     １　　３，８００　　配達\n" +
                        "パネル　　　     １　　２，０００　　配達\n" +
                        "技術料　　　     １　１５，０００\n" +
                        "出張費用　　     １　　５，０００\n" +
                        "-----------------------------------\n" +
                        "\n" +
                        "　　　　　　　　　　　小計　¥ ３５，８００\n" +
                        "　　　　　　　　　　　内税　¥ 　１，７９０\n" +
                        "　　　　　　　　　　　合計　¥ ３７，５９０\n" +
                        "\n" +
                        "　お問合わせ番号　　12345-67890\n";

        int      textSize = 24;
        Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);

        return createBitmapFromText(textToPrint, textSize, Constants.PAPER_SIZE_ESCPOS_THREE_INCH, typeface);
    }

    @Override
    public void appendEscPos3inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            try {
                encoding = Charset.forName("Shift-JIS");

                builder.appendCodePage(CodePageType.CP932);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendInternational(InternationalType.Japan);

        builder.appendCharacterSpace(0);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendEmphasis(true);

        builder.append("\n".getBytes());

        builder.appendMultipleHeight("スター電機\n".getBytes(encoding), 3);

        builder.appendMultipleHeight("修理報告書　兼領収書\n".getBytes(encoding), 2);

        builder.appendEmphasis(false);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------------\n" +
                        "発行日時：YYYY年MM月DD日HH時MM分\n" +
                        "TEL：054-347-XXXX\n" +
                        "\n" +
                        "           ｲｹﾆｼ  ｼｽﾞｺ   ｻﾏ\n" +
                        "　お名前：池西　静子　様\n" +
                        "　御住所：静岡市清水区七ツ新屋\n" +
                        "　　　　　５３６番地\n" +
                        "　伝票番号：No.12345-67890\n" +
                        "\n" +
                        "　この度は修理をご用命頂き有難うございます。\n" +
                        " 今後も故障など発生した場合はお気軽にご連絡ください。\n" +
                        "\n" +
                        "品名／型名        数量      金額　   備考\n" +
                        "------------------------------------------\n" +
                        "制御基板　          1     10,000     配達\n" +
                        "操作スイッチ        1      3,800     配達\n" +
                        "パネル　　          1      2,000     配達\n" +
                        "技術料　            1     15,000\n" +
                        "出張費用　　        1      5,000\n" +
                        "------------------------------------------\n" +
                        "\n" +
                        "                      小計       \\ 35,800\n" +
                        "                      内税       \\  1,790\n" +
                        "                      合計       \\ 37,590\n" +
                        "\n" +
                        "　お問合わせ番号　　12345-67890\n" +
                        "\n").getBytes(encoding));

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendBarcode("{BStar.".getBytes(Charset.forName("US-ASCII")), BarcodeSymbology.Code128, BarcodeWidth.Mode2, 40, true);
    }

    @Override
    public void appendDotImpact3inchTextReceiptData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            try {
                encoding = Charset.forName("Shift-JIS");

                builder.appendCodePage(CodePageType.CP932);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendInternational(InternationalType.Japan);

        builder.appendAlignment(AlignmentPosition.Center);

        builder.appendEmphasis(true);

        builder.appendMultipleHeight("スター電機\n修理報告書　兼領収書\n".getBytes(encoding), 2);

        builder.appendEmphasis(false);

        builder.appendAlignment(AlignmentPosition.Left);

        builder.append((
                "------------------------------------------\n" +
                        "発行日時：YYYY年MM月DD日HH時MM分\n" +
                        "TEL：054-347-XXXX\n" +
                        "\n" +
                        "        ｲｹﾆｼ  ｼｽﾞｺ  ｻﾏ\n" +
                        "　お名前：池西  静子　様\n" +
                        "　御住所：静岡市清水区七ツ新屋\n" +
                        "　　　　　５３６番地\n" +
                        "　伝票番号：No.12345-67890\n" +
                        "\n" +
                        "　この度は修理をご用命頂き有難うございます。\n" +
                        " 今後も故障など発生した場合はお気軽にご連絡ください。\n" +
                        "\n" +
                        "品名／型名　     数量      金額　     備考\n" +
                        "------------------------------------------\n" +
                        "制御基板　　       1      10,000     配達\n" +
                        "操作スイッチ       1       3,800     配達\n" +
                        "パネル　　　       1       2,000     配達\n" +
                        "技術料　　　       1      15,000\n" +
                        "出張費用　　       1       5,000\n" +
                        "------------------------------------------\n" +
                        "\n" +
                        "                       小計       \\ 35,800\n" +
                        "                       内税       \\  1,790\n" +
                        "                       合計       \\ 37,590\n" +
                        "\n" +
                        "　お問合わせ番号　　12345-67890\n").getBytes(encoding));
    }

    @Override
    public void appendTextLabelData(ICommandBuilder builder, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            try {
                encoding = Charset.forName("Shift-JIS");

                builder.appendCodePage(CodePageType.CP932);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendInternational(InternationalType.Japan);

        builder.appendCharacterSpace(0);

        builder.appendUnitFeed(20 * 2);

        builder.appendMultipleHeight(2);

        builder.append(("〒422-8654").getBytes(encoding));

        builder.appendUnitFeed(64);

        builder.append(("静岡県静岡市駿河区中吉田20番10号").getBytes(encoding));

        builder.appendUnitFeed(64);

        builder.append(("スター精密株式会社").getBytes(encoding));

        builder.appendUnitFeed(64);

        builder.appendMultipleHeight(1);
    }

    @Override
    public String createPasteTextLabelString() {
        return "〒422-8654\n" +
               "静岡県静岡市駿河区中吉田20番10号\n" +
               "スター精密株式会社";
    }

    @Override
    public void appendPasteTextLabelData(ICommandBuilder builder, String pasteText, boolean utf8) {
        Charset encoding;

        if (utf8) {
            encoding = Charset.forName("UTF-8");

            builder.appendCodePage(CodePageType.UTF8);
        }
        else {
            try {
                encoding = Charset.forName("Shift-JIS");

                builder.appendCodePage(CodePageType.CP932);
            }
            catch (UnsupportedCharsetException e) {
                encoding = Charset.forName("UTF-8");

                builder.appendCodePage(CodePageType.UTF8);
            }
        }

        builder.appendInternational(InternationalType.Japan);

        builder.appendCharacterSpace(0);

        builder.append(pasteText.getBytes(encoding));
    }
}
