# Introduction

This document details the project that wires angular front-end to JPA back-end

# Project structure

```bash
> mkdir -p learn/AngularSpring/05-angular-jpa/frontend
> cd learn/AngularSpring/05-angular-jpa/frontend
> ng new dukecart
> cd dukecart
> ng generate component componets/product-list
> ng generate component components/product-list
> ng generate class common/product
> tree src

src
├── app
│   ├── app.config.ts
│   ├── app.css
│   ├── app.html
│   ├── app.routes.ts
│   ├── app.spec.ts
│   ├── app.ts
│   ├── common
│   │   ├── product.spec.ts
│   │   └── product.ts
│   └── components
│       └── product-list
│           ├── product-list.css
│           ├── product-list.html
│           ├── product-list.spec.ts
│           └── product-list.ts
├── index.html
├── main.ts
└── styles.css

```

# Simple full stack

## Backend

Project [05-angular-jpa/backend](05-angular-jpa/backend) 

- Create entities (Product and Category) from database tables using Eclipse's JPATools
- Create `Repository` interfaces that implements `CrudRepository`
- Configure with `@CrossOrigin(<host-of-the-front-end>)` to allow front-end to access back-end
- Add project level config (overriding methods in `RepositoryRestConfigurer`) to disable select HTTP methods on select entities.

## Frontend

Project [05-angular-jpa/frontend](05-angular-jpa/frontend) 

Install bootstrap
```bash
> cd learn/AngularSpring/05-angular-jpa/frontend/dukecart

# Install bootstrap and font-awesome
> npm install bootstrap @fortawesome/fontawesome-free
added 2 packages, and audited 4 packages in 1s

# Check versions
> egrep "bootstrap|fontawesome" package.json
    "@fortawesome/fontawesome-free": "^7.1.0",
    "bootstrap": "^5.3.8"
```

## Pagination

### Setup

Install ng-bootstrap (Required for components like pagination)

```bash
> cd learn/AngularSpring/05-angular-jpa/frontend/dukecart

# ng-bootstrap is not yet updated for Angular-20. When it is updated the following command should work:
# Command: ng add @ng-bootstrap/ng-bootstrap
#
# Workaround mentioned: https://github.com/ng-bootstrap/ng-bootstrap/issues/4828#issuecomment-2925667913
# Update package.json as follows
> grep ng-bootstrap package.json 
    "@ng-bootstrap/ng-bootstrap": "github:sorcamarian/ng-bootstrap#only-src-folder",
```

### Angular data bindings

```html
<!-- product-list.html -->
<div class="card-footer">
  <ngb-pagination [(page)]="pageNumber" [pageSize]="pageSize" [collectionSize]="totalSize" (pageChange)="refreshProductList()" class="d-flex justify-content-center" />
</div>
```

| Binding Type    | Syntax                                                   | Detail                                                       |
| --------------- | -------------------------------------------------------- | ------------------------------------------------------------ |
| One-way binding | `[dom-element|component-prameter]=typescript-property`   | When back-end changes the front-end is updated automatically. |
| Two-way binding | `[(dom-element|component-prameter)]=typescript-property` | This is also called banana in a box binding. <br />When back-end typescript changes the UI reflects the change<br />When UI changes the back-end is updated. |
| Event binding   | `(event)=typescript-method`                              | When event occurs on the UI the corresponding method is invoked. |

## Service

Inter-component communication happens using service

Components ProductList and CartStatus

- ProuductList component `product-list.ts` lists the products with an "Add to cart" button
- Clicking on the button must increment the quantity and add the unit price and display the total in CartStatus component.
- This is achieved using CartService 

Working

- Component ProductList  `product-list.ts`  
  - ProductList injects service CartService. 
  - The button "Add to cart" is clicked in ProductList
  - ProudctList inturn invokes `this.cartService.addToCart(product)`
- Service CartService 
  - CartService has subject to which other components can subscribe to, when cart is updated
     `subjectCartUpdate: Subject<void> = new Subject<void>()`
  - Method `addToCart` of CartService updates the instance variables (`mapProductQuantity` `totalQuantity` and `totalPrice`)
  - Method `addToCart` invokes `this.subjectCartUpdate.next()`  this informing all subscribers that cart has been updated
- Component CartStatus `cart-status.ts`
  - CartStatus injects service . 
  - CartStatus subscribes to `cartService.subjectCartUpdate` as follows in `ngOnInit`
    `this.cartService.subjectCartUpdate.subscribe(data => { <fetch data from CartService and update instance variables in CartStatus> })`

> When there is a change to the cart, CartService updates its instance variables and informs all subscribers. 
> The subscribers in-turn update their instance variables from injected CartService instance
