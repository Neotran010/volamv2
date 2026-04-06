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
            Arrays.asList(
                "Vận khí La Hán hộ mệnh,",
                "kim cương bất hoại chi thân.",
                "Tăng giáp, kháng thương, đỡ đòn",
                "và sinh lực tối đa."),
            null, "LLL"));

        // === Lv.20 (Buff/Passive) - Chung ===
        ALL_SKILLS.add(new SkillInfo("dichcancuongthe", "§6Dịch Cân Cường Thể", "§aPassive",
            20, 0, 0,
            Arrays.asList(
                "Tu luyện Dịch Cân Kinh,",
                "cường hóa cân cốt, tráng kiện thể phách.",
                "Vĩnh viễn tăng ngoại công",
                "và nội kình."),
            null, null));

        // === Lv.30 (Passive) - Nhánh ===
        ALL_SKILLS.add(new SkillInfo("phanchankimcang", "§6Phản Chấn Kim Cang", "§aPassive",
            30, 0, 1,
            Arrays.asList(
                "Kim cang hộ thể, chấn phản ngoại lực.",
                "Khi đỡ đòn có tỉ lệ phản chấn",
                "sát thương lại kẻ địch."),
            Nhanh.THIEU_LAM_QUYEN, null));

        ALL_SKILLS.add(new SkillInfo("chandiahong", "§6Chấn Địa Hống", "§aPassive",
            30, 0, 3,
            Arrays.asList(
                "Sư tử hống chấn động đại địa.",
                "Đòn đánh gây thêm hiệu ứng",
                "choáng và mở rộng phạm vi",
                "công kích."),
            Nhanh.THIEU_LAM_BONG, null));

        ALL_SKILLS.add(new SkillInfo("mahachieny", "§6Ma Ha Chiến Ý", "§aPassive",
            30, 0, 0,
            Arrays.asList(
                "Chiến ý bùng phát, đao phong liệt sát.",
                "Tăng 15% tỉ lệ bạo kích",
                "và bạo kích sát thương."),
            Nhanh.THIEU_LAM_DAO, null));

        // === Lv.50 (Active) - Nhánh ===
        ALL_SKILLS.add(new SkillInfo("kimcanglienhoanchuong", "§bKim Cang Liên Hoàn Chưởng", "§cActive",
            50, 50, 15,
            Arrays.asList(
                "Kim cang thần quyền, liên hoàn bạo kích.",
                "Đấm liên tục về phía trước",
                "kèm hiệu ứng chấn choáng."),
            Nhanh.THIEU_LAM_QUYEN, "LRL"));

        ALL_SKILLS.add(new SkillInfo("hoanhtaothietlinh", "§bHoành Tảo Thiết Lĩnh", "§cActive",
            50, 50, 15,
            Arrays.asList(
                "Thiết bổng hoành tảo, quét sạch tiền phương.",
                "Đánh trực diện về phía trước,",
                "lan tỏa sát thương diện rộng."),
            Nhanh.THIEU_LAM_BONG, "LRL"));

        ALL_SKILLS.add(new SkillInfo("thieulamtramlienhoan", "§bThiếu Lâm Trảm Liên Hoàn", "§cActive",
            50, 50, 15,
            Arrays.asList(
                "Đao quang liên thiểm, trảm diệt liên hoàn.",
                "Chém liên tục về phía trước",
                "gây đa trọng sát thương."),
            Nhanh.THIEU_LAM_DAO, "LRL"));

        // === Lv.70 (Passive/Buff) - Chung ===
        ALL_SKILLS.add(new SkillInfo("batdongminhvuongan", "§6Bất Động Minh Vương Ấn", "§aPassive",
            70, 0, 0,
            Arrays.asList(
                "Minh Vương hộ ấn, bách độc bất xâm.",
                "Tăng kháng hiệu ứng khống chế",
                "và miễn dịch trạng thái bất lợi."),
            null, null));

        // === Lv.80 (Active) - Nhánh ===
        ALL_SKILLS.add(new SkillInfo("lahanloanda", "§bLa Hán Loạn Đả", "§cActive",
            80, 80, 25,
            Arrays.asList(
                "Thập Bát La Hán trận, loạn quyền cuồng vũ.",
                "Công kích diện rộng quanh thân,",
                "đồng thời nhận giảm sát thương."),
            Nhanh.THIEU_LAM_QUYEN, "LLRL"));

        ALL_SKILLS.add(new SkillInfo("vidatamkich", "§bVi Đà Tam Kích", "§cActive",
            80, 80, 25,
            Arrays.asList(
                "Vi Đà Hộ Pháp, tam kích chấn thiên.",
                "Đánh mục tiêu ba lần liên tiếp,",
                "đòn cuối hất tung đối phương."),
            Nhanh.THIEU_LAM_BONG, "LLRL"));

        ALL_SKILLS.add(new SkillInfo("votuongphaanhtram", "§bVô Tướng Phá Ảnh Trảm", "§cActive",
            80, 80, 25,
            Arrays.asList(
                "Đao khí tung hoành, phá ảnh trảm hình.",
                "Chém xuyên qua mục tiêu",
                "gây sát thương quán xuyến."),
            Nhanh.THIEU_LAM_DAO, "LLRL"));

        // === Lv.90 (Passive) - Nhánh ===
        ALL_SKILLS.add(new SkillInfo("kimthanlahan", "§6Kim Thân La Hán", "§aPassive",
            90, 0, 0,
            Arrays.asList(
                "Kim thân bất hoại, La Hán chân thân.",
                "Vĩnh viễn tăng sinh lực",
                "và phòng ngự lực."),
            Nhanh.THIEU_LAM_QUYEN, null));

        ALL_SKILLS.add(new SkillInfo("hophapkimchung", "§6Hộ Pháp Kim Chung", "§aPassive",
            90, 0, 0,
            Arrays.asList(
                "Kim Chung hộ pháp, vạn tà bất xâm.",
                "Tăng kháng khống chế",
                "và sinh lực tối đa."),
            Nhanh.THIEU_LAM_BONG, null));

        ALL_SKILLS.add(new SkillInfo("kimdiemattam", "§6Kim Diệm Sát Tâm", "§aPassive",
            90, 0, 0,
            Arrays.asList(
                "Kim diệm nhập tâm, nhất kích tất sát.",
                "Vĩnh viễn tăng tỉ lệ",
                "bạo kích."),
            Nhanh.THIEU_LAM_DAO, null));

        // === Lv.100 (Active) - Nhánh ===
        ALL_SKILLS.add(new SkillInfo("nhulaiathanchuong", "§4§lNhư Lai Thần Chưởng", "§cActive",
            100, 120, 45,
            Arrays.asList(
                "§4Tuyệt kỹ! §7Như Lai giáng thế, thần chưởng phá thiên.",
                "Đánh mục tiêu nhiều lần,",
                "đòn cuối bạo phát diện rộng",
                "kèm chấn choáng."),
            Nhanh.THIEU_LAM_QUYEN, "LRLRL"));

        ALL_SKILLS.add(new SkillInfo("phatmonlangkhongpha", "§4§lPhật Môn Lăng Không Phá", "§cActive",
            100, 120, 45,
            Arrays.asList(
                "§4Tuyệt kỹ! §7Phật lực vô biên, lăng không phá thế.",
                "Khống chế đối phương trên không,",
                "gây sát thương trọng kích",
                "không thể né tránh."),
            Nhanh.THIEU_LAM_BONG, "LRLRL"));

        ALL_SKILLS.add(new SkillInfo("thienlonggiangthe", "§4§lThiên Long Giáng Thế", "§cActive",
            100, 120, 45,
            Arrays.asList(
                "§4Tuyệt kỹ! §7Thiên long giáng thế, đao khí xung thiên.",
                "Triệu hồi long ảnh,",
                "gây sát thương diện rộng",
                "kèm thiêu đốt."),
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
