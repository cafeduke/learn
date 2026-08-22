import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { CategoryService } from '../../services/category.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Category } from '../../model/category';

@Component({
  selector: 'app-category-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './category-list.html',
  styleUrl: './category-list.css'
})
export class CategoryList  implements OnInit
{
  // Dependency injection
  private categoryService = inject(CategoryService);
  private route = inject(ActivatedRoute);

  // Instance variables
  listCategory: Category[] = [];

  constructor () { }

  /**
   * CallBack: Invoked once the category is initalized
   * The subscribe is invoked in async fashion, run in the background.
   * Once the data is available the product array is populated
   */
  ngOnInit()
  {
    this.route.paramMap.subscribe(params => {
      this.refreshCategoryList ();
    });
  }

  refreshCategoryList (): void
  {
    // See route.ts. The categoryId is configured as a param. Retrieve it. The default value is 1
    this.categoryService
      .getCategories()
      .subscribe(data => {this.listCategory = data});
  }
}
