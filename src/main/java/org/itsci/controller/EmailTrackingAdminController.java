package org.itsci.controller;

import org.itsci.model.EmailTrackingLog;
import org.itsci.service.EmailTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/email-tracking")
public class EmailTrackingAdminController {

    @Autowired
    private EmailTrackingService emailTrackingService;

    @GetMapping("/logs")
    public String viewTrackingLogs(Model model) {
        List<EmailTrackingLog> logs = emailTrackingService.getAllTrackingLogs();
        model.addAttribute("logs", logs);
        return "admin/email-tracking/logs";
    }
    
    @GetMapping("/logs/by-id")
    public String viewTrackingLogsByTrackingId(@RequestParam("id") String trackingId, Model model) {
        List<EmailTrackingLog> logs = emailTrackingService.getTrackingLogsByTrackingId(trackingId);
        model.addAttribute("logs", logs);
        model.addAttribute("trackingId", trackingId);
        return "admin/email-tracking/logs";
    }
    
    @PostMapping("/delete")
    public String deleteTrackingLog(@RequestParam("id") Long logId, 
                                   RedirectAttributes redirectAttributes) {
        emailTrackingService.deleteTrackingLog(logId);
        redirectAttributes.addFlashAttribute("message", "Tracking log deleted successfully");
        return "redirect:/admin/email-tracking/logs";
    }
    
    @PostMapping("/delete-by-tracking-id")
    public String deleteTrackingLogsByTrackingId(@RequestParam("trackingId") String trackingId,
                                               RedirectAttributes redirectAttributes) {
        int deletedCount = emailTrackingService.deleteTrackingLogsByTrackingId(trackingId);
        redirectAttributes.addFlashAttribute("message", 
            "Successfully deleted " + deletedCount + " tracking logs for ID: " + trackingId);
        return "redirect:/admin/email-tracking/logs";
    }
}
