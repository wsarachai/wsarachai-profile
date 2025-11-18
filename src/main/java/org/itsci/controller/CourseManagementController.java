package org.itsci.controller;

import org.itsci.controller.bean.CourseManagementBean;
import org.itsci.model.*;
import org.itsci.service.CourseManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/admin/course-management")
public class CourseManagementController {

  @Autowired
  private ResourceBundleMessageSource messageSource;

  @Autowired
  private CourseManagementService courseManagementService;

  /**
   * This method is used to trim all String fields in the form.
   * It will convert empty String to null.
   */
  @InitBinder
  public void initBinder(WebDataBinder dataBinder) {
    StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
    dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
  }

  /**
   * Display the main course management page
   */
  @GetMapping("")
  public String index(Model model, Authentication authentication,
      @RequestParam(name = "term", required = false) String term,
      @RequestParam(name = "year", required = false) String year) {
    try {
      // Determine if filtering by term/year is requested
      List<Course> courses;
      if (term != null && !term.isBlank() && year != null && !year.isBlank()) {
        String semester = term + "/" + year;
        courses = courseManagementService.findCoursesBySemester(semester);
        model.addAttribute("filteredSemester", semester);
      } else {
        // Load all courses with their sections and teacher assignments
        courses = courseManagementService.findAllCoursesWithDetails();
      }
      List<Subject> subjects = courseManagementService.findAllSubjects();
      List<Teacher> teachers = courseManagementService.findAllTeachers();
      List<Room> rooms = courseManagementService.findAllRooms();
      List<Curriculum> curriculums = courseManagementService.findAllCurriculums();

      model.addAttribute("courses", courses);
      model.addAttribute("subjects", subjects);
      model.addAttribute("teachers", teachers);
      model.addAttribute("rooms", rooms);
      model.addAttribute("courseManagementBean", new CourseManagementBean());
      model.addAttribute("curriculums", curriculums);

      return "admin/course-management/index";
    } catch (Exception e) {
      // Log the error for debugging
      e.printStackTrace();
      model.addAttribute("errorMessage", "Error loading course management data: " + e.getMessage());
      model.addAttribute("courses", new ArrayList<>());
      model.addAttribute("subjects", new ArrayList<>());
      model.addAttribute("teachers", new ArrayList<>());
      model.addAttribute("rooms", new ArrayList<>());
      model.addAttribute("courseManagementBean", new CourseManagementBean());
      return "admin/course-management/index";
    }
  }

  /**
   * Show form to create a new course
   */
  @GetMapping("/course/new")
  public String showCreateCourseForm(Model model) {
    List<Subject> subjects = courseManagementService.findAllSubjects();

    CourseManagementBean bean = new CourseManagementBean();
    model.addAttribute("courseManagementBean", bean);
    model.addAttribute("subjects", subjects);
    model.addAttribute("formAction", "create");

    return "admin/course-management/course-form";
  }

  /**
   * Create a new course
   */
  @PostMapping("/course/create")
  public String createCourse(@Valid @ModelAttribute("courseManagementBean") CourseManagementBean bean,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes redirectAttributes,
      Locale locale) {

    if (bindingResult.hasErrors()) {
      List<Subject> subjects = courseManagementService.findAllSubjects();
      model.addAttribute("subjects", subjects);
      model.addAttribute("formAction", "create");
      return "admin/course-management/course-form";
    }

    try {
      Course course = courseManagementService.createCourse(bean);
      String message = messageSource.getMessage("course.created.success",
          new Object[] { course.getSubject().getCode() }, locale);
      redirectAttributes.addFlashAttribute("successMessage", message);
    } catch (Exception e) {
      String message = messageSource.getMessage("course.created.error", null, locale);
      redirectAttributes.addFlashAttribute("errorMessage", message);
    }

    return "redirect:/admin/course-management";
  }

  /**
   * Show form to edit an existing course
   */
  @GetMapping("/course/edit/{id}")
  public String showEditCourseForm(@PathVariable Long id, Model model) {
    Course course = courseManagementService.findCourseById(id);
    List<Subject> subjects = courseManagementService.findAllSubjects();

    CourseManagementBean bean = new CourseManagementBean();
    bean.setCourseId(course.getId());
    bean.setSubjectId(course.getSubject().getId());
    bean.setSemester(course.getSemester());
    bean.setStartSemester(course.getStartSemester());

    model.addAttribute("courseManagementBean", bean);
    model.addAttribute("subjects", subjects);
    model.addAttribute("formAction", "edit");

    return "admin/course-management/course-form";
  }

  /**
   * Update an existing course
   */
  @PostMapping("/course/update")
  public String updateCourse(@Valid @ModelAttribute("courseManagementBean") CourseManagementBean bean,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes redirectAttributes,
      Locale locale) {

    if (bindingResult.hasErrors()) {
      List<Subject> subjects = courseManagementService.findAllSubjects();
      model.addAttribute("subjects", subjects);
      model.addAttribute("formAction", "edit");
      return "admin/course-management/course-form";
    }

    try {
      Course course = courseManagementService.updateCourse(bean);
      String message = messageSource.getMessage("course.updated.success",
          new Object[] { course.getSubject().getCode() }, locale);
      redirectAttributes.addFlashAttribute("successMessage", message);
    } catch (Exception e) {
      String message = messageSource.getMessage("course.updated.error", null, locale);
      redirectAttributes.addFlashAttribute("errorMessage", message);
    }

    return "redirect:/admin/course-management";
  }

  /**
   * Show form to create a new section
   */
  @GetMapping("/section/new/{courseId}")
  public String showCreateSectionForm(@PathVariable Long courseId, Model model) {
    Course course = courseManagementService.findCourseById(courseId);
    List<Room> rooms = courseManagementService.findAllRooms();

    CourseManagementBean bean = new CourseManagementBean();
    bean.setCourseId(courseId);

    model.addAttribute("courseManagementBean", bean);
    model.addAttribute("course", course);
    model.addAttribute("rooms", rooms);
    model.addAttribute("dayOfWeekValues", EDayOfWeek.values());
    model.addAttribute("formAction", "create");

    return "admin/course-management/section-form";
  }

  /**
   * Create a new section
   */
  @PostMapping("/section/create")
  public String createSection(@Valid @ModelAttribute("courseManagementBean") CourseManagementBean bean,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes redirectAttributes,
      Locale locale) {

    if (bindingResult.hasErrors()) {
      Course course = courseManagementService.findCourseById(bean.getCourseId());
      List<Room> rooms = courseManagementService.findAllRooms();

      model.addAttribute("course", course);
      model.addAttribute("rooms", rooms);
      model.addAttribute("dayOfWeekValues", EDayOfWeek.values());
      model.addAttribute("formAction", "create");
      return "admin/course-management/section-form";
    }

    try {
      Section section = courseManagementService.createSection(bean);
      String message = messageSource.getMessage("section.created.success",
          new Object[] { section.getGroupNumber() }, locale);
      redirectAttributes.addFlashAttribute("successMessage", message);
    } catch (Exception e) {
      String message = messageSource.getMessage("section.created.error", null, locale);
      redirectAttributes.addFlashAttribute("errorMessage", message);
    }

    return "redirect:/admin/course-management";
  }

  /**
   * Show form to edit an existing section
   */
  @GetMapping("/section/edit/{id}")
  public String showEditSectionForm(@PathVariable Long id, Model model) {
    Section section = courseManagementService.findSectionById(id);
    List<Room> rooms = courseManagementService.findAllRooms();

    CourseManagementBean bean = new CourseManagementBean();
    bean.setSectionId(section.getId());
    bean.setCourseId(section.getCourse().getId());
    bean.setGroupNumber(section.getGroupNumber());
    bean.setNumberOfSeat(section.getNumberOfSeat());
    bean.setLecDay(section.getLecDay());
    bean.setLabDay(section.getLabDay());
    bean.setStartLectureTime(section.getStartLectureTime());
    bean.setEndLectureTime(section.getEndLectureTime());
    bean.setStartLabTime(section.getStartLabTime());
    bean.setEndLabTime(section.getEndLabTime());
    bean.setLecRoomId(section.getLecRoom() != null ? section.getLecRoom().getId() : null);
    bean.setLabRoomId(section.getLabRoom() != null ? section.getLabRoom().getId() : null);

    model.addAttribute("courseManagementBean", bean);
    model.addAttribute("course", section.getCourse());
    model.addAttribute("rooms", rooms);
    model.addAttribute("dayOfWeekValues", EDayOfWeek.values());
    model.addAttribute("formAction", "edit");

    return "admin/course-management/section-form";
  }

  /**
   * Update an existing section
   */
  @PostMapping("/section/update")
  public String updateSection(@Valid @ModelAttribute("courseManagementBean") CourseManagementBean bean,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes redirectAttributes,
      Locale locale) {

    if (bindingResult.hasErrors()) {
      Course course = courseManagementService.findCourseById(bean.getCourseId());
      List<Room> rooms = courseManagementService.findAllRooms();

      model.addAttribute("course", course);
      model.addAttribute("rooms", rooms);
      model.addAttribute("dayOfWeekValues", EDayOfWeek.values());
      model.addAttribute("formAction", "edit");
      return "admin/course-management/section-form";
    }

    try {
      Section section = courseManagementService.updateSection(bean);
      String message = messageSource.getMessage("section.updated.success",
          new Object[] { section.getGroupNumber() }, locale);
      redirectAttributes.addFlashAttribute("successMessage", message);
    } catch (Exception e) {
      String message = messageSource.getMessage("section.updated.error", null, locale);
      redirectAttributes.addFlashAttribute("errorMessage", message);
    }

    return "redirect:/admin/course-management";
  }

  /**
   * Assign teacher to course
   */
  @PostMapping("/assign-teacher")
  public String assignTeacher(@RequestParam Long courseId,
      @RequestParam Long teacherId,
      @RequestParam(defaultValue = "active") String status,
      RedirectAttributes redirectAttributes,
      Locale locale) {

    try {
      courseManagementService.assignTeacherToCourse(teacherId, courseId, status);
      String message = messageSource.getMessage("teacher.assigned.success", null, locale);
      redirectAttributes.addFlashAttribute("successMessage", message);
    } catch (Exception e) {
      String message = messageSource.getMessage("teacher.assigned.error", null, locale);
      redirectAttributes.addFlashAttribute("errorMessage", message);
    }

    return "redirect:/admin/course-management";
  }

  /**
   * Remove teacher assignment from course
   */
  @PostMapping("/remove-teacher-assignment/{id}")
  public String removeTeacherAssignment(@PathVariable Long id,
      RedirectAttributes redirectAttributes,
      Locale locale) {

    try {
      courseManagementService.removeTeacherAssignment(id);
      String message = messageSource.getMessage("teacher.assignment.removed.success", null, locale);
      redirectAttributes.addFlashAttribute("successMessage", message);
    } catch (Exception e) {
      String message = messageSource.getMessage("teacher.assignment.removed.error", null, locale);
      redirectAttributes.addFlashAttribute("errorMessage", message);
    }

    return "redirect:/admin/course-management";
  }

  /**
   * Delete a course
   */
  @PostMapping("/course/delete/{id}")
  public String deleteCourse(@PathVariable Long id,
      RedirectAttributes redirectAttributes,
      Locale locale) {

    try {
      courseManagementService.deleteCourse(id);
      String message = messageSource.getMessage("course.deleted.success", null, locale);
      redirectAttributes.addFlashAttribute("successMessage", message);
    } catch (Exception e) {
      String message = messageSource.getMessage("course.deleted.error", null, locale);
      redirectAttributes.addFlashAttribute("errorMessage", message);
    }

    return "redirect:/admin/course-management";
  }

  /**
   * Delete a section
   */
  @PostMapping("/section/delete/{id}")
  public String deleteSection(@PathVariable Long id,
      RedirectAttributes redirectAttributes,
      Locale locale) {

    try {
      courseManagementService.deleteSection(id);
      String message = messageSource.getMessage("section.deleted.success", null, locale);
      redirectAttributes.addFlashAttribute("successMessage", message);
    } catch (Exception e) {
      String message = messageSource.getMessage("section.deleted.error", null, locale);
      redirectAttributes.addFlashAttribute("errorMessage", message);
    }

    return "redirect:/admin/course-management";
  }

  /**
   * Test endpoint to isolate the TeacherDao issue
   */
  @GetMapping("/test-teachers")
  @ResponseBody
  public String testTeachers() {
    try {
      List<Teacher> teachers = courseManagementService.findAllTeachers();
      return "Successfully retrieved " + teachers.size() + " teachers";
    } catch (Exception e) {
      return "Error: " + e.getMessage() + " - " + e.getClass().getSimpleName();
    }
  }
}
