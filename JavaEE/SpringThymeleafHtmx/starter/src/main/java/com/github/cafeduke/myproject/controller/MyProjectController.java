package com.github.cafeduke.myproject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.FragmentsRendering;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyProjectController
{
  @GetMapping("/")
  public String home()
  {
    return "demo";
  }

  @GetMapping("/daisy")
  public String daisy()
  {
    return "daisy";
  }

  @GetMapping("/demoA/update")
  public String inputUpdate(@RequestParam(name = "name") String name, Model model)
  {
    model.addAttribute("name", name);
    return "demo::fragDemoA1";
  }

  /**
   * Return multiple pairs of model and view
   *  - A view is a string of format "path/to/tempate/filename-minus-extension :: fragment-name"
   *  - A model is a Map<String,?> -- map of attributeName to any object attributeValue
   *  - A ModelAndView object wraps view and the corresponding model
   *  - Several ModelAndView objects are added to List and returned
   * @param name
   * @return
   */
  @GetMapping("/demoB/update")
  public List<ModelAndView> inputUpdateDemoB(@RequestParam(name = "name") String name)
  {
    String viewA = "demo::fragDemoB1";
    Map<String, ?> modelA = Map.of("name", name);

    String viewB = "demo::fragDemoB2";
    Map<String, ?> modelB = Map.of("count", (name == null) ? 0 : name.length());

    return List.of(new ModelAndView(viewA, modelA), new ModelAndView(viewB, modelB));
  }

  /**
   * A FragmentRendering return type does the same job as List<ModelAndView>, but provides a
   * cleaner way to build fragments.
   *
   * @param name
   * @return
   */
  @GetMapping("/demoC/update")
  public FragmentsRendering inputUpdateDemoC(@RequestParam(name = "name") String name)
  {
    String viewA = "fragments/demoC::fragDemoC1";
    Map<String, Object> modelA = Map.of("name", name);

    String viewB = "fragments/demoC::fragDemoC2";
    Map<String, Object> modelB = Map.of("count", (name == null) ? 0 : name.length());

    return FragmentsRendering
      .fragment(viewA, modelA)
      .fragment(viewB, modelB)
      .build();
  }
}
