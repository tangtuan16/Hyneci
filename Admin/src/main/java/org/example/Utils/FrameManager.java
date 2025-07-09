package org.example.Utils;

import org.example.Views.BookFrame;
import org.example.Views.CategoryFrame;
import org.example.Views.UserFrame;

public class FrameManager {
    private static BookFrame bookFrame;
    private static UserFrame userFrame;
    private static CategoryFrame categoryFrame;

    public static void showBookFrame() {
        if (bookFrame == null || !bookFrame.isDisplayable()) {
            bookFrame = new BookFrame();
        }
        bookFrame.setVisible(true);
        bookFrame.toFront();
    }

    public static void showUserFrame() {
        if (userFrame == null || !userFrame.isDisplayable()) {
            userFrame = new UserFrame();
        }
        userFrame.setVisible(true);
        userFrame.toFront();
    }

    public static void showCategoryFrame() {
        if (categoryFrame == null || !categoryFrame.isDisplayable()) {
            categoryFrame = new CategoryFrame();
        }
        categoryFrame.setVisible(true);
        categoryFrame.toFront();
    }

    public static void closeAll() {
        if (bookFrame != null) bookFrame.dispose();
        if (userFrame != null) userFrame.dispose();
        if (categoryFrame != null) categoryFrame.dispose();
    }
}
