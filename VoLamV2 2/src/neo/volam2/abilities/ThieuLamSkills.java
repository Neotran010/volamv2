package neo.volam2.abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import neo.volam2.data.Nhanh;

public class ThieuLamSkills {

    private static final List<SkillInfo> ALL_SKILLS = new ArrayList<>();

    static {
        // === Lv.10 (Buff) - Chung ===
        ALL_SKILLS.add(new SkillInfo("lahanhothe", "§6La Hán Hộ Thể", "§eBuff",
            10, 30, 30,
            Arrays.asList("Nhận thêm kháng sát thương, giáp,", "đỡ đòn và tăng máu tối đa."),
            null, "LLL"));

        // === Lv.20 (Buff/Passive) - Chung ===
        ALL_SKILLS.add(new SkillInfo("dichcancuongthe", "§6Dịch Cân Cường Thể", "§aPassive",
            20, 0, 0,
            Arrays.asList("Tăng thể chất & sinh khí."),
            null, null));

        // === Lv.30 (Passive) - Nhánh ===
        ALL_SKILLS.add(new SkillInfo("phanchankimcang", "§6Phản Chấn Kim Cang", "§aPassive",
            30, 0, 1,
            Arrays.asList("Khi đỡ đòn có tỉ lệ phản đòn", "lại kẻ địch."),
            Nhanh.THIEU_LAM_QUYEN, null));

        ALL_SKILLS.add(new SkillInfo("chandiahong", "§6Chấn Địa Hống", "§aPassive",
            30, 0, 3,
            Arrays.asList("Đòn đánh gây thêm choáng", "và tăng diện rộng."),
            Nhanh.THIEU_LAM_BONG, null));

        ALL_SKILLS.add(new SkillInfo("mahachieny", "§6Ma Ha Chiến Ý", "§aPassive",
            30, 0, 0,
            Arrays.asList("Tăng 15% tỉ lệ chí mạng", "và sát thương chí mạng."),
            Nhanh.THIEU_LAM_DAO, null));

        // === Lv.50 (Active) - Nhánh ===
        ALL_SKILLS.add(new SkillInfo("kimcanglienhoanchuong", "§b Kim Cang Liên Hoàn Chưởng", "§cActive",
            50, 50, 15,
            Arrays.asList("Đấm liên tục về phía trước", "kèm choáng."),
            Nhanh.THIEU_LAM_QUYEN, "LRL"));

        ALL_SKILLS.add(new SkillInfo("hoanhtaothietlinh", "§bHoành Tảo Thiết Lĩnh", "§cActive",
            50, 50, 15,
            Arrays.asList("Đánh trực diện về phía trước,", "lan ra sau vài block."),
            Nhanh.THIEU_LAM_BONG, "LRL"));

        ALL_SKILLS.add(new SkillInfo("thieulamtramlienhoan", "§bThiếu Lâm Trảm Liên Hoàn", "§cActive",
            50, 50, 15,
            Arrays.asList("Chém liên tục về phía trước."),
            Nhanh.THIEU_LAM_DAO, "LRL"));

        // === Lv.70 (Passive/Buff) - Chung ===
        ALL_SKILLS.add(new SkillInfo("batdongminhvuongan", "§6Bất Động Minh Vương Ấn", "§aPassive",
            70, 0, 0,
            Arrays.asList("Tăng kháng hiệu ứng", "và kháng bất lợi."),
            null, null));

        // === Lv.80 (Active) - Nhánh ===
        ALL_SKILLS.add(new SkillInfo("lahanloanda", "§bLa Hán Loạn Đả", "§cActive",
            80, 80, 25,
            Arrays.asList("Tấn công AOE quanh bản thân,", "nhận kháng sát thương."),
            Nhanh.THIEU_LAM_QUYEN, "LLRL"));

        ALL_SKILLS.add(new SkillInfo("vidatamkich", "§bVi Đà Tam Kích", "§cActive",
            80, 80, 25,
            Arrays.asList("Đánh mục tiêu 3 lần,", "lần cuối hất tung."),
            Nhanh.THIEU_LAM_BONG, "LLRL"));

        ALL_SKILLS.add(new SkillInfo("votuongphaanhtram", "§bVô Tướng Phá Ảnh Trảm", "§cActive",
            80, 80, 25,
            Arrays.asList("Chém các nhát xuyên qua", "mục tiêu chỉ định."),
            Nhanh.THIEU_LAM_DAO, "LLRL"));

        // === Lv.90 (Passive) - Nhánh ===
        ALL_SKILLS.add(new SkillInfo("kimthanlahan", "§6Kim Thân La Hán", "§aPassive",
            90, 0, 0,
            Arrays.asList("Tăng sinh lực, phòng thủ."),
            Nhanh.THIEU_LAM_QUYEN, null));

        ALL_SKILLS.add(new SkillInfo("hophapkimchung", "§6Hộ Pháp Kim Chung", "§aPassive",
            90, 0, 0,
            Arrays.asList("Tăng kháng khống chế, sinh lực."),
            Nhanh.THIEU_LAM_BONG, null));

        ALL_SKILLS.add(new SkillInfo("kimdiemattam", "§6Kim Diệm Sát Tâm", "§aPassive",
            90, 0, 0,
            Arrays.asList("Tăng tỉ lệ chí mạng."),
            Nhanh.THIEU_LAM_DAO, null));

        // === Lv.100 (Active) - Nhánh ===
        ALL_SKILLS.add(new SkillInfo("nhulaiathanchuong", "§4§lNhư Lai Thần Chưởng", "§cActive",
            100, 120, 45,
            Arrays.asList("Đánh mục tiêu, đòn cuối", "AOE + choáng."),
            Nhanh.THIEU_LAM_QUYEN, "LRLRL"));

        ALL_SKILLS.add(new SkillInfo("phatmonlangkhongpha", "§4§lPhật Môn Lăng Không Phá", "§cActive",
            100, 120, 45,
            Arrays.asList("Khống chế trên không,", "gây sát thương."),
            Nhanh.THIEU_LAM_BONG, "LRLRL"));

        ALL_SKILLS.add(new SkillInfo("thienlonggiangthe", "§4§lThiên Long Giáng Thế", "§cActive",
            100, 120, 45,
            Arrays.asList("Triệu hồi long ảnh", "gây sát thương lan, thiêu đốt."),
            Nhanh.THIEU_LAM_DAO, "LRLRL"));
    }

    public static List<SkillInfo> getAllSkillInfos() {
        return ALL_SKILLS;
    }

    public static SkillInfo getById(String id) {
        for (SkillInfo s : ALL_SKILLS) {
            if (s.getId().equals(id)) return s;
        }
        return null;
    }
}
