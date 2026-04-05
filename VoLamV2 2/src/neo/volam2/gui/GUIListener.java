package neo.volam2.gui;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import neo.volam2.abilities.SkillInfo;
import neo.volam2.abilities.ThieuLamSkills;
import neo.volam2.data.MonPhai;
import neo.volam2.data.Nhanh;
import neo.volam2.data.PlayerData;
import neo.volam2.data.PlayerDataManager;
import neo.volam2.data.TocHe;
import neo.volam2.main.U;
import neo.volam2.tiemnang.TiemNangManager;
import neo.volam2.tiemnang.TiemNangType;

public class GUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player p = (Player) e.getWhoClicked();
        String title = e.getView().getTitle();

        if (title.equals(ChonTocGUI.TITLE)) {
            e.setCancelled(true);
            handleChonToc(p, e);
        } else if (title.equals(ChonMonPhaiGUI.TITLE)) {
            e.setCancelled(true);
            handleChonMonPhai(p, e);
        } else if (title.equals(ThongTinNhanVatGUI.TITLE)) {
            e.setCancelled(true);
            handleThongTin(p, e);
        } else if (title.equals(KyNangGUI.TITLE)) {
            e.setCancelled(true);
            handleKyNang(p, e);
        } else if (title.startsWith(NangCapKyNangGUI.TITLE_PREFIX) || title.equals("§0§l✦ §a§lNâng Cấp: §a§lKỹ Năng §0§l✦")) {
            e.setCancelled(true);
            handleNangCapKyNang(p, e);
        } else if (title.equals(TiemNangGUI.TITLE)) {
            e.setCancelled(true);
            handleTiemNang(p, e);
        } else if (title.equals(ChuyenNhanhGUI.TITLE)) {
            e.setCancelled(true);
            handleChuyenNhanh(p, e);
        } else if (title.equals(ChuyenPhaiGUI.TITLE)) {
            e.setCancelled(true);
            handleChuyenPhai(p, e);
        }
    }

    // === CHON TOC ===
    private void handleChonToc(Player p, InventoryClickEvent e) {
        if (!U.isClickTop(e)) return;
        int slot = e.getSlot();
        PlayerData data = PlayerDataManager.get(p);
        if (data == null) return;

        TocHe selected = null;
        switch (slot) {
            case 10: selected = TocHe.KIM; break;
            case 11: selected = TocHe.MOC; break;
            case 13: selected = TocHe.THUY; break;
            case 15: selected = TocHe.HOA; break;
            case 16: selected = TocHe.THO; break;
            default: return;
        }

        data.setTocHe(selected);
        p.sendMessage("§a§l[VÕ LÂM] §aĐã chọn tộc hệ: " + selected.getDisplayName());
        U.playSound(p, Sound.ENTITY_PLAYER_LEVELUP);
        PlayerDataManager.save(p);
        p.closeInventory();

        // Open class selection
        ChonMonPhaiGUI.open(p);
    }

    // === CHON MON PHAI ===
    private void handleChonMonPhai(Player p, InventoryClickEvent e) {
        if (!U.isClickTop(e)) return;
        int slot = e.getSlot();
        PlayerData data = PlayerDataManager.get(p);
        if (data == null || !data.hasTocHe()) return;

        // Back button
        if (slot == 22) {
            ChonTocGUI.open(p);
            return;
        }

        MonPhai[] monPhais = MonPhai.getByTocHe(data.getTocHe());
        int[] slots = {11, 13, 15};
        MonPhai selected = null;
        for (int i = 0; i < monPhais.length && i < slots.length; i++) {
            if (slot == slots[i]) {
                selected = monPhais[i];
                break;
            }
        }

        if (selected == null) return;

        if (selected.isMaintenance()) {
            p.sendMessage("§c§l[VÕ LÂM] §cMôn phái này đang bảo trì!");
            U.playSound(p, Sound.ENTITY_VILLAGER_NO);
            return;
        }

        data.setMonPhai(selected);
        data.setNhanh(Nhanh.getDefault(selected));
        data.setTriggerChooseClass(false);
        p.sendMessage("§a§l[VÕ LÂM] §aĐã gia nhập: " + selected.getDisplayName());
        U.playSound(p, Sound.ENTITY_PLAYER_LEVELUP);
        PlayerDataManager.save(p);
        p.closeInventory();
    }

    // === THONG TIN NHAN VAT ===
    private void handleThongTin(Player p, InventoryClickEvent e) {
        if (!U.isClickTop(e)) return;
        int slot = e.getSlot();

        switch (slot) {
            case 38: // Ky Nang
                KyNangGUI.open(p);
                break;
            case 40: // Tiem Nang
                TiemNangGUI.open(p);
                break;
            case 42: // Chuyen Nhanh
                ChuyenNhanhGUI.open(p);
                break;
            case 44: // Chuyen Phai
                ChuyenPhaiGUI.open(p);
                break;
            case 49: // Close
                p.closeInventory();
                break;
        }
    }

    // === KY NANG ===
    private void handleKyNang(Player p, InventoryClickEvent e) {
        if (!U.isClickTop(e)) return;
        ItemStack item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;

        // Close button
        if (item.getType() == Material.BARRIER || item.getType() == Material.ACACIA_DOOR) {
            p.closeInventory();
            return;
        }

        // Find matching skill by display name
        PlayerData data = PlayerDataManager.get(p);
        if (data == null) return;

        List<SkillInfo> skills = KyNangGUI.getSkillsForPlayer(data);
        String displayName = item.getItemMeta().getDisplayName();

        for (SkillInfo skill : skills) {
            if (skill.getDisplayName().equals(displayName)) {
                int currentLevel = data.getSkillLevel(skill.getId());
                boolean unlocked = data.getLevel() >= skill.getRequiredLevel();
                boolean isBranch = skill.getBranch() != null;
                boolean branchMatch = !isBranch || (data.getNhanh() != null && data.getNhanh() == skill.getBranch());

                if (!unlocked) {
                    p.sendMessage("§c§l[KỸ NĂNG] §cChưa đủ cấp độ! Cần level " + skill.getRequiredLevel());
                    U.playSound(p, Sound.ENTITY_VILLAGER_NO);
                    return;
                }
                if (isBranch && !branchMatch) {
                    p.sendMessage("§c§l[KỸ NĂNG] §cKỹ năng này không thuộc nhánh vũ khí của bạn!");
                    U.playSound(p, Sound.ENTITY_VILLAGER_NO);
                    return;
                }

                NangCapKyNangGUI.open(p, skill.getId());
                return;
            }
        }
    }

    // === NANG CAP KY NANG ===
    private void handleNangCapKyNang(Player p, InventoryClickEvent e) {
        if (!U.isClickTop(e)) return;
        int slot = e.getSlot();

        // Back
        if (slot == 22) {
            KyNangGUI.open(p);
            return;
        }

        // Upgrade
        if (slot == 13) {
            ItemStack item = e.getCurrentItem();
            if (item == null || item.getType() != Material.LIME_CONCRETE) return;

            PlayerData data = PlayerDataManager.get(p);
            if (data == null) return;

            // Find which skill from title
            String title = e.getView().getTitle();
            if (data.getMonPhai() == MonPhai.THIEU_LAM) {
                for (SkillInfo skill : ThieuLamSkills.getAllSkillInfos()) {
                    String expectedTitle = NangCapKyNangGUI.TITLE_PREFIX + skill.getDisplayName() + " §0§l✦";
                    if (expectedTitle.length() > 32) {
                        expectedTitle = NangCapKyNangGUI.TITLE_PREFIX + "§a§lKỹ Năng §0§l✦";
                    }
                    if (title.equals(expectedTitle)) {
                        int currentLevel = data.getSkillLevel(skill.getId());
                        if (currentLevel >= 10) {
                            p.sendMessage("§c§l[KỸ NĂNG] §cĐã đạt cấp tối đa!");
                            return;
                        }
                        if (data.getAvailableDiemKyNang() <= 0) {
                            p.sendMessage("§c§l[KỸ NĂNG] §cKhông đủ điểm kỹ năng!");
                            return;
                        }

                        data.setSkillLevel(skill.getId(), currentLevel + 1);
                        p.sendMessage("§a§l[KỸ NĂNG] §aNâng cấp " + skill.getDisplayName() +
                            " §athành công! §7(Lv." + (currentLevel + 1) + ")");
                        U.playSound(p, Sound.ENTITY_PLAYER_LEVELUP);
                        PlayerDataManager.save(p);
                        NangCapKyNangGUI.open(p, skill.getId());
                        return;
                    }
                }
            }
        }
    }

    // === TIEM NANG ===
    private void handleTiemNang(Player p, InventoryClickEvent e) {
        if (!U.isClickTop(e)) return;
        int slot = e.getSlot();

        switch (slot) {
            case 10: // Suc Manh
                TiemNangManager.addPoint(p, TiemNangType.SUC_MANH);
                TiemNangGUI.open(p);
                break;
            case 12: // Than Phap
                TiemNangManager.addPoint(p, TiemNangType.THAN_PHAP);
                TiemNangGUI.open(p);
                break;
            case 14: // Noi Cong
                TiemNangManager.addPoint(p, TiemNangType.NOI_CONG);
                TiemNangGUI.open(p);
                break;
            case 16: // The Luc
                TiemNangManager.addPoint(p, TiemNangType.THE_LUC);
                TiemNangGUI.open(p);
                break;
            case 31: // Close
                p.closeInventory();
                break;
        }
    }

    // === CHUYEN NHANH ===
    private void handleChuyenNhanh(Player p, InventoryClickEvent e) {
        if (!U.isClickTop(e)) return;
        int slot = e.getSlot();

        // Back
        if (slot == 22) {
            ThongTinNhanVatGUI.open(p);
            return;
        }

        PlayerData data = PlayerDataManager.get(p);
        if (data == null || !data.hasMonPhai()) return;

        Nhanh[] branches = Nhanh.getByMonPhai(data.getMonPhai());
        int[] slots = {10, 13, 16};
        for (int i = 0; i < branches.length && i < slots.length; i++) {
            if (slot == slots[i]) {
                Nhanh n = branches[i];
                if (n == data.getNhanh()) {
                    p.sendMessage("§c§l[VÕ LÂM] §cBạn đang sử dụng nhánh này rồi!");
                    return;
                }

                // Check cost
                double money = U.getMoney(p);
                if (money < ChuyenNhanhGUI.COST) {
                    p.sendMessage("§c§l[VÕ LÂM] §cKhông đủ xu! Cần §6" + ChuyenNhanhGUI.COST + " xu");
                    U.playSound(p, Sound.ENTITY_VILLAGER_NO);
                    return;
                }

                U.costMoney(p, ChuyenNhanhGUI.COST);
                data.setNhanh(n);
                p.sendMessage("§a§l[VÕ LÂM] §aĐã chuyển nhánh: " + n.getDisplayName());
                U.playSound(p, Sound.ENTITY_PLAYER_LEVELUP);
                PlayerDataManager.save(p);
                ChuyenNhanhGUI.open(p);
                return;
            }
        }
    }

    // === CHUYEN PHAI ===
    private void handleChuyenPhai(Player p, InventoryClickEvent e) {
        if (!U.isClickTop(e)) return;
        int slot = e.getSlot();

        // Back
        if (slot == 22) {
            ThongTinNhanVatGUI.open(p);
            return;
        }

        PlayerData data = PlayerDataManager.get(p);
        if (data == null || !data.hasTocHe()) return;

        MonPhai[] monPhais = MonPhai.getByTocHe(data.getTocHe());
        int[] slots = {11, 13, 15};
        for (int i = 0; i < monPhais.length && i < slots.length; i++) {
            if (slot == slots[i]) {
                MonPhai mp = monPhais[i];
                if (mp == data.getMonPhai()) {
                    p.sendMessage("§c§l[VÕ LÂM] §cBạn đang ở môn phái này rồi!");
                    return;
                }
                if (mp.isMaintenance()) {
                    p.sendMessage("§c§l[VÕ LÂM] §cMôn phái này đang bảo trì!");
                    U.playSound(p, Sound.ENTITY_VILLAGER_NO);
                    return;
                }

                double money = U.getMoney(p);
                if (money < ChuyenPhaiGUI.COST) {
                    p.sendMessage("§c§l[VÕ LÂM] §cKhông đủ xu! Cần §6" + ChuyenPhaiGUI.COST + " xu");
                    U.playSound(p, Sound.ENTITY_VILLAGER_NO);
                    return;
                }

                U.costMoney(p, ChuyenPhaiGUI.COST);
                data.setMonPhai(mp);
                data.setNhanh(Nhanh.getDefault(mp));
                p.sendMessage("§a§l[VÕ LÂM] §aĐã chuyển phái: " + mp.getDisplayName());
                U.playSound(p, Sound.ENTITY_PLAYER_LEVELUP);
                PlayerDataManager.save(p);
                ChuyenPhaiGUI.open(p);
                return;
            }
        }
    }
}
