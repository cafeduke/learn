import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  imports: [],
  templateUrl: './search.html',
  styleUrl: './search.css'
})
export class Search  implements OnInit
{
  // Dependency injection
  private router = inject(Router);

  constructor () { }

  /**
   * CallBack: Invoked once the component is initalized
   * The subscribe is invoked in async fashion, run in the background.
   * Once the data is available the product array is populated
   */
  ngOnInit()   {  }

  doSearch (searchKey: string)
  {
    this.router.navigateByUrl(`/search/${searchKey}`);
  }
}
