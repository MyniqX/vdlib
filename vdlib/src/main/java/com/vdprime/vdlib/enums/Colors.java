package com.vdprime.vdlib.enums;
import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.vdprime.vdlib.R;
import com.vdprime.vdlib.utils.vdutils;

public enum Colors
{
    white(R.color.white, "#FFFFFF"),
    ivory(R.color.ivory, "#FFFFF0"),
    lightyellow(R.color.lightyellow, "#FFFFE0"),
    yellow(R.color.yellow, "#FFFF00"),
    snow(R.color.snow, "#FFFAFA"),
    floralwhite(R.color.floralwhite, "#FFFAF0"),
    lemonchiffon(R.color.lemonchiffon, "#FFFACD"),
    cornsilk(R.color.cornsilk, "#FFF8DC"),
    seashell(R.color.seashell, "#FFF5EE"),
    lavenderblush(R.color.lavenderblush, "#FFF0F5"),
    papayawhip(R.color.papayawhip, "#FFEFD5"),
    blanchedalmond(R.color.blanchedalmond, "#FFEBCD"),
    mistyrose(R.color.mistyrose, "#FFE4E1"),
    bisque(R.color.bisque, "#FFE4C4"),
    moccasin(R.color.moccasin, "#FFE4B5"),
    navajowhite(R.color.navajowhite, "#FFDEAD"),
    peachpuff(R.color.peachpuff, "#FFDAB9"),
    gold(R.color.gold, "#FFD700"),
    pink(R.color.pink, "#FFC0CB"),
    lightpink(R.color.lightpink, "#FFB6C1"),
    orange(R.color.orange, "#FFA500"),
    lightsalmon(R.color.lightsalmon, "#FFA07A"),
    darkorange(R.color.darkorange, "#FF8C00"),
    coral(R.color.coral, "#FF7F50"),
    hotpink(R.color.hotpink, "#FF69B4"),
    tomato(R.color.tomato, "#FF6347"),
    orangered(R.color.orangered, "#FF4500"),
    deeppink(R.color.deeppink, "#FF1493"),
    fuchsia(R.color.fuchsia, "#FF00FF"),
    magenta(R.color.magenta, "#FF00FF"),
    red(R.color.red, "#FF0000"),
    oldlace(R.color.oldlace, "#FDF5E6"),
    lightgoldenrodyellow(R.color.lightgoldenrodyellow, "#FAFAD2"),
    linen(R.color.linen, "#FAF0E6"),
    antiquewhite(R.color.antiquewhite, "#FAEBD7"),
    salmon(R.color.salmon, "#FA8072"),
    ghostwhite(R.color.ghostwhite, "#F8F8FF"),
    mintcream(R.color.mintcream, "#F5FFFA"),
    whitesmoke(R.color.whitesmoke, "#F5F5F5"),
    beige(R.color.beige, "#F5F5DC"),
    wheat(R.color.wheat, "#F5DEB3"),
    sandybrown(R.color.sandybrown, "#F4A460"),
    azure(R.color.azure, "#F0FFFF"),
    honeydew(R.color.honeydew, "#F0FFF0"),
    aliceblue(R.color.aliceblue, "#F0F8FF"),
    khaki(R.color.khaki, "#F0E68C"),
    lightcoral(R.color.lightcoral, "#F08080"),
    palegoldenrod(R.color.palegoldenrod, "#EEE8AA"),
    violet(R.color.violet, "#EE82EE"),
    darksalmon(R.color.darksalmon, "#E9967A"),
    lavender(R.color.lavender, "#E6E6FA"),
    lightcyan(R.color.lightcyan, "#E0FFFF"),
    burlywood(R.color.burlywood, "#DEB887"),
    plum(R.color.plum, "#DDA0DD"),
    gainsboro(R.color.gainsboro, "#DCDCDC"),
    crimson(R.color.crimson, "#DC143C"),
    palevioletred(R.color.palevioletred, "#DB7093"),
    goldenrod(R.color.goldenrod, "#DAA520"),
    orchid(R.color.orchid, "#DA70D6"),
    thistle(R.color.thistle, "#D8BFD8"),
    lightgrey(R.color.lightgrey, "#D3D3D3"),
    tan(R.color.tan, "#D2B48C"),
    chocolate(R.color.chocolate, "#D2691E"),
    peru(R.color.peru, "#CD853F"),
    indianred(R.color.indianred, "#CD5C5C"),
    mediumvioletred(R.color.mediumvioletred, "#C71585"),
    silver(R.color.silver, "#C0C0C0"),
    darkkhaki(R.color.darkkhaki, "#BDB76B"),
    rosybrown(R.color.rosybrown, "#BC8F8F"),
    mediumorchid(R.color.mediumorchid, "#BA55D3"),
    darkgoldenrod(R.color.darkgoldenrod, "#B8860B"),
    firebrick(R.color.firebrick, "#B22222"),
    powderblue(R.color.powderblue, "#B0E0E6"),
    lightsteelblue(R.color.lightsteelblue, "#B0C4DE"),
    paleturquoise(R.color.paleturquoise, "#AFEEEE"),
    greenyellow(R.color.greenyellow, "#ADFF2F"),
    lightblue(R.color.lightblue, "#ADD8E6"),
    darkgray(R.color.darkgray, "#A9A9A9"),
    brown(R.color.brown, "#A52A2A"),
    sienna(R.color.sienna, "#A0522D"),
    yellowgreen(R.color.yellowgreen, "#9ACD32"),
    darkorchid(R.color.darkorchid, "#9932CC"),
    palegreen(R.color.palegreen, "#98FB98"),
    darkviolet(R.color.darkviolet, "#9400D3"),
    mediumpurple(R.color.mediumpurple, "#9370DB"),
    lightgreen(R.color.lightgreen, "#90EE90"),
    darkseagreen(R.color.darkseagreen, "#8FBC8F"),
    saddlebrown(R.color.saddlebrown, "#8B4513"),
    darkmagenta(R.color.darkmagenta, "#8B008B"),
    darkred(R.color.darkred, "#8B0000"),
    blueviolet(R.color.blueviolet, "#8A2BE2"),
    lightskyblue(R.color.lightskyblue, "#87CEFA"),
    skyblue(R.color.skyblue, "#87CEEB"),
    gray(R.color.gray, "#808080"),
    olive(R.color.olive, "#808000"),
    purple(R.color.purple, "#800080"),
    maroon(R.color.maroon, "#800000"),
    aquamarine(R.color.aquamarine, "#7FFFD4"),
    chartreuse(R.color.chartreuse, "#7FFF00"),
    lawngreen(R.color.lawngreen, "#7CFC00"),
    mediumslateblue(R.color.mediumslateblue, "#7B68EE"),
    lightslategray(R.color.lightslategray, "#778899"),
    slategray(R.color.slategray, "#708090"),
    olivedrab(R.color.olivedrab, "#6B8E23"),
    slateblue(R.color.slateblue, "#6A5ACD"),
    dimgray(R.color.dimgray, "#696969"),
    mediumaquamarine(R.color.mediumaquamarine, "#66CDAA"),
    cornflowerblue(R.color.cornflowerblue, "#6495ED"),
    cadetblue(R.color.cadetblue, "#5F9EA0"),
    darkolivegreen(R.color.darkolivegreen, "#556B2F"),
    indigo(R.color.indigo, "#4B0082"),
    mediumturquoise(R.color.mediumturquoise, "#48D1CC"),
    darkslateblue(R.color.darkslateblue, "#483D8B"),
    steelblue(R.color.steelblue, "#4682B4"),
    royalblue(R.color.royalblue, "#4169E1"),
    turquoise(R.color.turquoise, "#40E0D0"),
    mediumseagreen(R.color.mediumseagreen, "#3CB371"),
    limegreen(R.color.limegreen, "#32CD32"),
    darkslategray(R.color.darkslategray, "#2F4F4F"),
    seagreen(R.color.seagreen, "#2E8B57"),
    forestgreen(R.color.forestgreen, "#228B22"),
    lightseagreen(R.color.lightseagreen, "#20B2AA"),
    dodgerblue(R.color.dodgerblue, "#1E90FF"),
    midnightblue(R.color.midnightblue, "#191970"),
    aqua(R.color.aqua, "#00FFFF"),
    cyan(R.color.cyan, "#00FFFF"),
    springgreen(R.color.springgreen, "#00FF7F"),
    lime(R.color.lime, "#00FF00"),
    mediumspringgreen(R.color.mediumspringgreen, "#00FA9A"),
    darkturquoise(R.color.darkturquoise, "#00CED1"),
    deepskyblue(R.color.deepskyblue, "#00BFFF"),
    darkcyan(R.color.darkcyan, "#008B8B"),
    teal(R.color.teal, "#008080"),
    green(R.color.green, "#008000"),
    darkgreen(R.color.darkgreen, "#006400"),
    blue(R.color.blue, "#0000FF"),
    mediumblue(R.color.mediumblue, "#0000CD"),
    darkblue(R.color.darkblue, "#00008B"),
    navy(R.color.navy, "#000080"),
    black(R.color.black, "#000000"),
    transparent(R.color.transparent, "#80000000"),;
    int    ID;
    int    color;
    String hexString;
    Drawable drawable = null;
    
    private Colors(final int id, final String hex)
    {
        ID = id;
        hexString = hex;
        color = Color.parseColor(hex);
    }
    
    public static int getRandomColor()
    {
        return Color.rgb(vdutils.random.nextInt(255), vdutils.random.nextInt(255), vdutils.random.nextInt(255));
    }
    
    public static Colors getRandom()
    {
        final Colors[] v = values();
        final int      r = vdutils.random.nextInt(v.length - 1);
        return v[r];
    }
    
    public static Colors getIndex(final int i)
    {
        final Colors[] v     = values();
        final int      index = Math.abs(i) % v.length;
        return v[index];
    }
    
    public int getResourceID()
    {
        return ID;
    }
    
    @TargetApi (Build.VERSION_CODES.LOLLIPOP) @SuppressWarnings ("deprecation")
    public Drawable getDrawable(final Resources res)
    {
        if (drawable == null)
        {
            drawable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                       ? res.getDrawable(ID,
                                         null)
                       : res.getDrawable(ID);
        }
        return drawable;
    }
    
    public String getHex()
    {
        return hexString;
    }
    
    public int getColor()
    {
        return color;
    }
}
