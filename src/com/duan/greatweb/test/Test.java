package com.duan.greatweb.test;

import com.duan.greatweb.dao.NoteDao;
import com.duan.greatweb.dao.NoteDaoImpl;
import com.duan.greatweb.entitly.Note;

import java.util.Random;

public class Test {

    public static void main(String[] args) {
        NoteDao n = new NoteDaoImpl();
        int[] uids = {1, 2, 3, 4, 5, 6, 7};
        String[] titles = {
                "我们都是戏子",
                "寂寞横生",
                "不如有用优雅的方式，看起来比较不失礼。",
                "早已经散落天涯",
                "而我却不知道。",
                "新手帮助",
                "玩法介绍",
                "投诉建议",};
        String[] contents = {
                "有人会因为你的缺陷而讨厌你，但是，也会有人因为你的真实天然而喜欢你。",
                "青春就是疯狂的奔跑，然后华丽地跌倒。",
                "记忆是很任性的。你想记的可能都记不得，你想忘的可能都忘不掉。",
                "有时候，坚持了你最不想干的事情之后，会得到你最想要的东西。",
                "能让内心保持宁静的人，才是最有力量的人。所谓“神静而心和，心和而形全;神躁则心荡，心荡则形伤。",
                "老天在送你一个大礼物时，都会用重重困难做包装。",
                "你在乎，或者不在乎我，我就在角落，不来不去。",
                "跌跌撞撞才明白了许多，舍不得却又无可奈何。如果最后，陪在你身边的不是我，请不要怪我，因为我真的尽力了。与其脸红脖子的骂人，不如有用优雅的方式，看起来比较不失礼。我们都是戏子，在别人的故事里，流着自己的眼泪。",};

        Random r = new Random();

        for (int i = 0; i < 20; i++) {
            Note note = new Note();
            note.setTitle(titles[r.nextInt(titles.length)]);
            note.setContent(contents[r.nextInt(contents.length)]);
            note.setUserId(uids[r.nextInt(6) + 1]);
            note.setDateTime(System.currentTimeMillis());
            n.addNote(note);
        }
    }
}
