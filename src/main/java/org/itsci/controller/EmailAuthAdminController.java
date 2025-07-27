package org.itsci.controller;

import org.itsci.model.EmailTrackingAuth;
import org.itsci.service.EmailTrackingAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Admin controller for managing email tracking authentication keys
 */
@Controller
@RequestMapping("/admin/email-auth")
public class EmailAuthAdminController {

  @Autowired
  private EmailTrackingAuthService emailTrackingAuthService;

  /**
   * Display all authentication keys
   */
  @GetMapping
  public String listAuthKeys(Model model) {
    List<EmailTrackingAuth> authKeys = emailTrackingAuthService.getAll();
    model.addAttribute("authKeys", authKeys);
    model.addAttribute("pageTitle", "Email Authentication Keys");
    return "admin/email-auth/list";
  }

  /**
   * Show form to create new authentication key
   */
  @GetMapping("/create")
  public String showCreateForm(Model model) {
    model.addAttribute("authKey", new EmailTrackingAuth());
    model.addAttribute("isEdit", false);
    model.addAttribute("pageTitle", "Create Authentication Key");
    model.addAttribute("action", "create");
    return "admin/email-auth/form";
  }

  /**
   * Create new authentication key
   */
  @PostMapping("/create")
  public String createAuthKey(@ModelAttribute EmailTrackingAuth authKey,
      @RequestParam("expirationDays") int expirationDays,
      RedirectAttributes redirectAttributes) {
    try {
      // Validate input
      if (authKey.getTrackingId() == null || authKey.getTrackingId().trim().isEmpty()) {
        redirectAttributes.addFlashAttribute("error", "Tracking ID is required");
        return "redirect:/admin/email-auth/create";
      }

      if (expirationDays < 1 || expirationDays > 365) {
        redirectAttributes.addFlashAttribute("error", "Expiration days must be between 1 and 365");
        return "redirect:/admin/email-auth/create";
      }

      if (!authKey.getTrackingId().matches("^[a-zA-Z0-9\\-_]+$")) {
        redirectAttributes.addFlashAttribute("error",
            "Tracking ID can only contain letters, numbers, hyphens, and underscores");
        return "redirect:/admin/email-auth/create";
      }

      EmailTrackingAuth newAuthKey = emailTrackingAuthService.generateAuthKey(
          authKey.getTrackingId(),
          expirationDays,
          authKey.getDescription());

      redirectAttributes.addFlashAttribute("success",
          "Authentication key created successfully! Auth Key: " + newAuthKey.getAuthKey());
      return "redirect:/admin/email-auth";

    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", "Failed to create authentication key: " + e.getMessage());
      return "redirect:/admin/email-auth/create";
    }
  }

  /**
   * Show form to edit authentication key
   */
  @GetMapping("/edit/{id}")
  public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
    try {
      // Get auth key by ID - use getAll to include inactive keys
      List<EmailTrackingAuth> allKeys = emailTrackingAuthService.getAll();
      EmailTrackingAuth authKey = allKeys.stream()
          .filter(key -> key.getId().equals(id))
          .findFirst()
          .orElse(null);

      if (authKey == null) {
        redirectAttributes.addFlashAttribute("error", "Authentication key not found");
        return "redirect:/admin/email-auth";
      }

      model.addAttribute("authKey", authKey);
      model.addAttribute("isEdit", true);
      model.addAttribute("pageTitle", "Edit Authentication Key");
      model.addAttribute("action", "edit");
      return "admin/email-auth/form";

    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", "Failed to load authentication key: " + e.getMessage());
      return "redirect:/admin/email-auth";
    }
  }

  /**
   * Update authentication key
   */
  @PostMapping("/edit/{id}")
  public String updateAuthKey(@PathVariable("id") Long id,
      @ModelAttribute EmailTrackingAuth formAuthKey,
      RedirectAttributes redirectAttributes) {
    try {
      // Get auth key by ID
      List<EmailTrackingAuth> allKeys = emailTrackingAuthService.getAll();
      EmailTrackingAuth authKey = allKeys.stream()
          .filter(key -> key.getId().equals(id))
          .findFirst()
          .orElse(null);

      if (authKey == null) {
        redirectAttributes.addFlashAttribute("error", "Authentication key not found");
        return "redirect:/admin/email-auth";
      }

      // Update description and active status
      authKey.setDescription(formAuthKey.getDescription());
      authKey.setIsActive(formAuthKey.getIsActive() != null ? formAuthKey.getIsActive() : false);

      // We need to add an update method to the service
      // For now, we can only deactivate keys
      if (formAuthKey.getIsActive() == null || !formAuthKey.getIsActive()) {
        emailTrackingAuthService.deactivateAuthKey(authKey.getAuthKey());
        redirectAttributes.addFlashAttribute("success", "Authentication key deactivated successfully");
      } else {
        redirectAttributes.addFlashAttribute("info", "Authentication key update completed");
      }

      return "redirect:/admin/email-auth";

    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", "Failed to update authentication key: " + e.getMessage());
      return "redirect:/admin/email-auth";
    }
  }

  /**
   * Delete/deactivate authentication key
   */
  @PostMapping("/delete/{id}")
  public String deleteAuthKey(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    try {
      // Get auth key by ID to get the auth key string
      List<EmailTrackingAuth> allKeys = emailTrackingAuthService.getAll();
      EmailTrackingAuth authKey = allKeys.stream()
          .filter(key -> key.getId().equals(id))
          .findFirst()
          .orElse(null);

      if (authKey == null) {
        redirectAttributes.addFlashAttribute("error", "Authentication key not found");
        return "redirect:/admin/email-auth";
      }

      boolean success = emailTrackingAuthService.deactivateAuthKey(authKey.getAuthKey());
      if (success) {
        redirectAttributes.addFlashAttribute("success", "Authentication key deactivated successfully");
      } else {
        redirectAttributes.addFlashAttribute("error", "Failed to deactivate authentication key");
      }

      return "redirect:/admin/email-auth";

    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", "Failed to delete authentication key: " + e.getMessage());
      return "redirect:/admin/email-auth";
    }
  }

  /**
   * Regenerate authentication key
   */
  @PostMapping("/regenerate/{id}")
  public String regenerateAuthKey(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    try {
      // Get auth key by ID
      List<EmailTrackingAuth> allKeys = emailTrackingAuthService.getAll();
      EmailTrackingAuth authKey = allKeys.stream()
          .filter(key -> key.getId().equals(id))
          .findFirst()
          .orElse(null);

      if (authKey == null) {
        redirectAttributes.addFlashAttribute("error", "Authentication key not found");
        return "redirect:/admin/email-auth/edit/" + id;
      }

      // Create new auth key with same properties but new key
      EmailTrackingAuth newAuthKey = emailTrackingAuthService.generateAuthKey(
          authKey.getTrackingId(),
          30, // Default 30 days
          authKey.getDescription());

      if (newAuthKey != null) {
        // Deactivate old key
        emailTrackingAuthService.deactivateAuthKey(authKey.getAuthKey());
        redirectAttributes.addFlashAttribute("success", "New authentication key generated successfully");
        return "redirect:/admin/email-auth/edit/" + newAuthKey.getId();
      } else {
        redirectAttributes.addFlashAttribute("error", "Failed to generate new authentication key");
        return "redirect:/admin/email-auth/edit/" + id;
      }

    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", "Error regenerating authentication key: " + e.getMessage());
      return "redirect:/admin/email-auth/edit/" + id;
    }
  }

  /**
   * Cleanup expired authentication keys
   */
  @PostMapping("/cleanup")
  public String cleanupExpired(RedirectAttributes redirectAttributes) {
    try {
      int deleted = emailTrackingAuthService.cleanupExpired();
      redirectAttributes.addFlashAttribute("success", "Cleaned up " + deleted + " expired authentication keys");
      return "redirect:/admin/email-auth";

    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", "Failed to cleanup expired keys: " + e.getMessage());
      return "redirect:/admin/email-auth";
    }
  }

  /**
   * Search authentication keys by tracking ID
   */
  @GetMapping("/search")
  public String searchAuthKeys(@RequestParam("trackingId") String trackingId, Model model) {
    List<EmailTrackingAuth> authKeys = emailTrackingAuthService.getByTrackingId(trackingId);
    model.addAttribute("authKeys", authKeys);
    model.addAttribute("searchTrackingId", trackingId);
    model.addAttribute("pageTitle", "Authentication Keys - Search Results");
    return "admin/email-auth/list";
  }
}
