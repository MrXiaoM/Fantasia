package top.mrxiaom.fantasia;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Utils {

    public static String readAsString(File file) {
        StringBuilder result = new StringBuilder();
        try(
                FileInputStream input = new FileInputStream(file);
                InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(reader)) {
            String lineTxt;
            boolean a = false;
            // 逐行读取
            while ((lineTxt = br.readLine()) != null) {
                // 输出内容到控制台
                if (a)  result.append("\n");
                else a = true;
                result.append(lineTxt);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void saveFromString(File file, String content) {
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(content);
            out.flush();
            out.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    public static void drawRect(float left, float top, float right, float bottom, float thickness, float alpha, float red, float green, float blue) {
        fillRect(left - thickness, top, left, bottom, alpha, red, green, blue);
        fillRect(right, top, right + thickness, bottom, alpha, red, green, blue);
        fillRect(left - thickness, top - thickness, right + thickness, top, alpha, red, green, blue);
        fillRect(left - thickness, bottom, right + thickness, bottom + thickness, alpha, red, green, blue);
    }

    public static void fillRect(float left, float top, float right, float bottom, float alpha, float red, float green, float blue)
    {
        if (left < right)
        {
            float i = left;
            left = right;
            right = i;
        }

        if (top < bottom)
        {
            float j = top;
            top = bottom;
            bottom = j;
        }
        alpha /= 255.0f;
        red /= 255.0f;
        green /= 255.0f;
        blue /= 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(red, green, blue, alpha);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, 0.0D).endVertex();
        bufferbuilder.pos(right, bottom, 0.0D).endVertex();
        bufferbuilder.pos(right, top, 0.0D).endVertex();
        bufferbuilder.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}