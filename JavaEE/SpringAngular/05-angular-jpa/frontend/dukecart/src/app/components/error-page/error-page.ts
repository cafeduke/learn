import { JsonPipe, KeyValuePipe } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

/**
 * Routing to error pages
 * ----------------------
 * When you use router.navigate(['/error'], { state: { ... } }), Angular uses the browser's native history API
 * to add an entry to the browser's session history stack.
 *
 * The state object we provide becomes the history.state for that specific history entry.
 */
@Component({
  selector: 'app-error-page',
  imports: [KeyValuePipe, RouterLink],
  templateUrl: './error-page.html',
  styleUrl: './error-page.css',
})

export class ErrorPage implements OnInit
{
  // Dependency injection
  private readonly router = inject(Router);

  // Instance variables
  code:number = 0;
  summary:string = "";
  detail:string = "";
  mapProperties: Map<string, string|number> = new Map();

  ngOnInit(): void
  {
    // Access state from history
    const state = history.state;

    if (!state)
      return;

    this.code = state.responseCode;
    this.summary = state.errorSummary;
    this.detail = state.errorDetail;
    console.log(`[Error] code=${this.code} summary=${this.summary} detail=${state.detail}`);
    this.mapProperties = new Map(Object.entries(state));
  }

  keys (obj: any): string[]
  {
    return Object.keys(obj);
  }
}
