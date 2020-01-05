package com.rlocatelli.orgchart.service;

import com.rlocatelli.orgchart.bean.OrgData;
import com.rlocatelli.orgchart.bean.TreeOrg;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author reinaldo
 */
public class OrgChart {

    private final int SPACE_BETWEEN_ORG = 80;
    private final int SPACE_BETWEEN_LEVEL = 250;
    private final int PADDING = 20;
    private final int STROKE = 8;
    private final Color COLOR_LINE = new Color(37, 87, 38);

    public void getOrgParent(TreeOrg treeOrg, File file) {

        BufferedImage bufferedImage = getOrgPanel(treeOrg, false);

        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException ex) {
            Logger.getLogger(OrgChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void configureGraphics2D(Graphics2D g2d) {
        //Font
        Font currentFont = g2d.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.FAMILY, currentFont.getFamily());
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_SEMIBOLD);
        attributes.put(TextAttribute.SIZE, (int) (currentFont.getSize() * 5.4));
        Font font = Font.getFont(attributes);

        //Stroke
        float[] fa = {10, 10, 10};
        BasicStroke bs = new BasicStroke(STROKE);
        g2d.setStroke(bs);

        g2d.setFont(font);
    }

    public BufferedImage getOrgPanel(TreeOrg treeOrg, boolean addMarginTop) {

        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        configureGraphics2D(g2d);

        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        OrgData orgData = treeOrg.getOrgData();

        int panelWidth = PADDING * 2 + STROKE * 2;
        int panelHeight = metrics.getHeight() * 2 + STROKE * 2 + PADDING * 2;

        int descriptionWidth = metrics.stringWidth(orgData.getDescription());
        int nameWidth = metrics.stringWidth(orgData.getName());

        if (descriptionWidth > nameWidth) {
            panelWidth += descriptionWidth;
        } else {
            panelWidth += nameWidth;
        }

        if (Objects.nonNull(treeOrg.getChildrens())) {
            for (int i = 0; i < treeOrg.getChildrens().size(); i++) {

                BufferedImage panelChildren = getOrgPanel(treeOrg.getChildrens().get(i), true);
                if (panelChildren != null) {
                    int imageHeight = bufferedImage.getHeight();
                    if (panelChildren.getHeight() > imageHeight) {
                        imageHeight = panelChildren.getHeight();
                    }

                    int bufferedImageNewWidth = bufferedImage.getWidth() + panelChildren.getWidth();
                    if (i != 0) {
                        bufferedImageNewWidth += SPACE_BETWEEN_ORG;
                    }

                    BufferedImage bufferedImageNew = new BufferedImage(bufferedImageNewWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g = bufferedImageNew.createGraphics();
                    configureGraphics2D(g);
                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, bufferedImageNew.getWidth(), bufferedImageNew.getHeight());
                    g.drawImage(bufferedImage, 0, 0, null);
                    g.drawImage(panelChildren, bufferedImageNew.getWidth() - panelChildren.getWidth(), 0, null);

                    //Draw Line
                    g.setColor(COLOR_LINE);

                    if (i != 0) {
                        int lineY = SPACE_BETWEEN_LEVEL / 2 + STROKE / 2;
                        int panel1X = bufferedImage.getWidth() / 2 + STROKE + 1;
                        int panel2X = bufferedImage.getWidth() + panelChildren.getWidth() / 2 + SPACE_BETWEEN_ORG;

                        g.drawLine(panel1X, lineY, panel2X, lineY);

                        g.drawLine(bufferedImage.getWidth() + panelChildren.getWidth() / 2 + SPACE_BETWEEN_ORG, lineY, bufferedImage.getWidth() + panelChildren.getWidth() / 2 + SPACE_BETWEEN_ORG, lineY + SPACE_BETWEEN_LEVEL / 2);
                    } else {
                        int lineY = SPACE_BETWEEN_LEVEL / 2 + STROKE;
                        if (treeOrg.getChildrens().size() == 1) {
                            lineY = 0;
                        }
                        g.drawLine(bufferedImage.getWidth() + panelChildren.getWidth() / 2 + STROKE, lineY, bufferedImage.getWidth() + panelChildren.getWidth() / 2 + STROKE, SPACE_BETWEEN_LEVEL);

                    }

                    bufferedImage = bufferedImageNew;

                    //Line Up
                    if (i == treeOrg.getChildrens().size() - 1 && Objects.nonNull(treeOrg.getChildrens()) && treeOrg.getChildrens().size() > 1) {
                        g.drawLine(bufferedImage.getWidth() / 2, 0, bufferedImage.getWidth() / 2, SPACE_BETWEEN_LEVEL / 2);
                    }
                    g.dispose();
                }
            }

        }

        int bufferedImageFinalWidth = panelWidth;
        if (bufferedImage.getWidth() > bufferedImageFinalWidth) {
            bufferedImageFinalWidth = bufferedImage.getWidth();
        }

        int bufferedImageFinalHeight = bufferedImage.getHeight() + panelHeight + STROKE * 2;
        int y = 0;
        if (addMarginTop) {
            bufferedImageFinalHeight += SPACE_BETWEEN_LEVEL;
            y = SPACE_BETWEEN_LEVEL;
        }

        BufferedImage bufferedImageFinal = new BufferedImage(bufferedImageFinalWidth + STROKE * 2, bufferedImageFinalHeight, BufferedImage.TYPE_INT_RGB);
        g2d = bufferedImageFinal.createGraphics();
        configureGraphics2D(g2d);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, bufferedImageFinal.getWidth(), bufferedImageFinal.getHeight());

        //Panel
        int panelX = (bufferedImageFinalWidth / 2) - (panelWidth / 2) + STROKE;
        int panelY = y + STROKE;
        g2d.setColor(COLOR_LINE);
        g2d.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 30, 30);

        //Name
        setOrgName(treeOrg.getOrgData().getName(), panelX, panelY, panelWidth, g2d, metrics);

        //Description
        setOrgDescription(treeOrg.getOrgData().getDescription(), panelX, panelY, panelWidth, g2d, metrics);

        g2d.drawImage(bufferedImage, 0, panelHeight + y + STROKE * 2 - STROKE / 2, null);
        g2d.dispose();
        return bufferedImageFinal;
    }

    private void setOrgName(String name, int panelX, int panelY, int panelWidth, Graphics2D g2d, FontMetrics metrics) {
        int textHeight = metrics.getHeight();
        int textWidth = metrics.stringWidth(name);

        g2d.setColor(COLOR_LINE);
        g2d.fillRect(panelX, panelY, panelWidth, textHeight);

        g2d.setColor(Color.WHITE);
        g2d.drawString(name, panelX + (panelWidth - textWidth) / 2, textHeight + panelY - 15);
    }

    private void setOrgDescription(String description, int panelX, int panelY, int panelWidth, Graphics2D g2d, FontMetrics metrics) {
        int textHeight = metrics.getHeight();
        int textWidth = metrics.stringWidth(description);

        g2d.setColor(COLOR_LINE);
        g2d.drawString(description, panelX + (panelWidth - textWidth) / 2, textHeight * 2 + PADDING + panelY);
    }

}
