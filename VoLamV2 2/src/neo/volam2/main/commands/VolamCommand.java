package neo.volam2.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import neo.volam2.data.MonPhai;
import neo.volam2.data.Nhanh;
import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.tiemnang.TiemNangManager;

public class VolamCommand implements CommandExecutor {

    private static final String PREFIX = "§6§l[VÕ LÂM ADMIN] ";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "setlevel":
                handleSetLevel(sender, args);
                break;
            case "addtiemnang":
                handleAddTiemNang(sender, args);
                break;
            case "addkynang":
                handleAddKyNang(sender, args);
                break;
            case "setmonphai":
                handleSetMonPhai(sender, args);
                break;
            case "info":
                handleInfo(sender, args);
                break;
            case "save":
                handleSave(sender);
                break;
            default:
                sendHelp(sender);
                break;
        }

        return true;
    }

    private void handleSetLevel(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(PREFIX + "§c/volam setlevel <player> <level>");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(PREFIX + "§cKhông tìm thấy người chơi: " + args[1]);
            return;
        }

        int level;
        try {
            level = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(PREFIX + "§cLevel không hợp lệ: " + args[2]);
            return;
        }

        if (level < 1) {
            sender.sendMessage(PREFIX + "§cLevel phải >= 1");
            return;
        }

        PlayerData data = PlayerDataManager.get(target);
        if (data == null) return;

        data.setLevel(level);
        PlayerDataManager.save(target);
        TiemNangManager.applyStats(target);
        sender.sendMessage(PREFIX + "§aĐã set level của §f" + target.getName() + " §athành §f" + level);
    }

    private void handleAddTiemNang(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(PREFIX + "§c/volam addtiemnang <player> <points>");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(PREFIX + "§cKhông tìm thấy người chơi: " + args[1]);
            return;
        }

        int points;
        try {
            points = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(PREFIX + "§cSố điểm không hợp lệ: " + args[2]);
            return;
        }

        PlayerData data = PlayerDataManager.get(target);
        if (data == null) return;

        data.setDiemTiemNangAdd(data.getDiemTiemNangAdd() + points);
        PlayerDataManager.save(target);
        sender.sendMessage(PREFIX + "§aĐã thêm §f" + points + " §ađiểm tiềm năng cho §f" + target.getName());
    }

    private void handleAddKyNang(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(PREFIX + "§c/volam addkynang <player> <points>");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(PREFIX + "§cKhông tìm thấy người chơi: " + args[1]);
            return;
        }

        int points;
        try {
            points = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(PREFIX + "§cSố điểm không hợp lệ: " + args[2]);
            return;
        }

        PlayerData data = PlayerDataManager.get(target);
        if (data == null) return;

        data.setDiemKyNangAdd(data.getDiemKyNangAdd() + points);
        PlayerDataManager.save(target);
        sender.sendMessage(PREFIX + "§aĐã thêm §f" + points + " §ađiểm kỹ năng cho §f" + target.getName());
    }

    private void handleSetMonPhai(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(PREFIX + "§c/volam setmonphai <player> <monphai>");
            sender.sendMessage(PREFIX + "§7Danh sách: THIEU_LAM, THIEN_VUONG, DUONG_MON, NGU_DOC, NGA_MY, CAI_BANG, THIEN_NHAN, VO_DANG, CON_LON");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(PREFIX + "§cKhông tìm thấy người chơi: " + args[1]);
            return;
        }

        MonPhai monPhai = MonPhai.fromName(args[2]);
        if (monPhai == null) {
            sender.sendMessage(PREFIX + "§cMôn phái không hợp lệ: " + args[2]);
            return;
        }

        PlayerData data = PlayerDataManager.get(target);
        if (data == null) return;

        data.setTocHe(monPhai.getTocHe());
        data.setMonPhai(monPhai);
        data.setNhanh(Nhanh.getDefault(monPhai));
        PlayerDataManager.save(target);
        sender.sendMessage(PREFIX + "§aĐã set môn phái của §f" + target.getName() + " §athành " + monPhai.getDisplayName());
    }

    private void handleInfo(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(PREFIX + "§c/volam info <player>");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(PREFIX + "§cKhông tìm thấy người chơi: " + args[1]);
            return;
        }

        PlayerData data = PlayerDataManager.get(target);
        if (data == null) {
            sender.sendMessage(PREFIX + "§cKhông có dữ liệu cho: " + args[1]);
            return;
        }

        sender.sendMessage("§6§m------------------");
        sender.sendMessage(PREFIX + "§eThông tin: §f" + target.getName());
        sender.sendMessage("§7Level: §f" + data.getLevel());
        sender.sendMessage("§7Tộc hệ: " + (data.hasTocHe() ? data.getTocHe().getDisplayName() : "§cChưa chọn"));
        sender.sendMessage("§7Môn phái: " + (data.hasMonPhai() ? data.getMonPhai().getDisplayName() : "§cChưa chọn"));
        sender.sendMessage("§7Nhánh: " + (data.getNhanh() != null ? data.getNhanh().getDisplayName() : "§cChưa chọn"));
        sender.sendMessage("§7Sức Mạnh: §c" + data.getSucManh());
        sender.sendMessage("§7Thân Pháp: §a" + data.getThanPhap());
        sender.sendMessage("§7Nội Công: §9" + data.getNoiCong());
        sender.sendMessage("§7Thể Lực: §e" + data.getTheLuc());
        sender.sendMessage("§7Điểm tiềm năng: §b" + data.getAvailableDiemTiemNang() + "§7/§f" + data.getTotalDiemTiemNang());
        sender.sendMessage("§7Điểm kỹ năng: §b" + data.getAvailableDiemKyNang() + "§7/§f" + data.getTotalDiemKyNang());
        sender.sendMessage("§6§m------------------");
    }

    private void handleSave(CommandSender sender) {
        PlayerDataManager.saveAll();
        sender.sendMessage(PREFIX + "§aĐã lưu tất cả dữ liệu người chơi!");
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§6§m------------------");
        sender.sendMessage("§6§lVõ Lâm V2 §7- §eAdmin Commands");
        sender.sendMessage("§e/volam setlevel <player> <level> §7- Set level");
        sender.sendMessage("§e/volam addtiemnang <player> <points> §7- Thêm điểm tiềm năng");
        sender.sendMessage("§e/volam addkynang <player> <points> §7- Thêm điểm kỹ năng");
        sender.sendMessage("§e/volam setmonphai <player> <monphai> §7- Set môn phái");
        sender.sendMessage("§e/volam info <player> §7- Xem thông tin người chơi");
        sender.sendMessage("§e/volam save §7- Lưu tất cả dữ liệu");
        sender.sendMessage("§6§m------------------");
    }
}
