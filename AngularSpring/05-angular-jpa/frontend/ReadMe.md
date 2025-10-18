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

Project [05-angular-jpa/backend](05-angular-jpa/backend) 

- Create entities (Product and Category) from database tables using Eclipse's JPATools
- Create `Repository` interfaces that implements `CrudRepository`
- Configure with `@CrossOrigin(<host-of-the-front-end>)` to allow front-end to access back-end
- Add project level config (overriding methods in `RepositoryRestConfigurer`) to disable select HTTP methods on select entities.

Project [05-angular-jpa/frontend](05-angular-jpa/frontend) 

- Install bootstrap
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

  

- 





