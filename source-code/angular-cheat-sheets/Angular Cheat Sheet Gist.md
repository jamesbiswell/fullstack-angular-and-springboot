# Angular Cheat Sheet

A TypeScript-based web application framework developed by Google for building scalable single-page applications with a complete solution including routing, forms, HTTP client, and more.

---

## Core Concepts

Angular is a full-featured framework that uses TypeScript and follows the MVC pattern. It provides a complete solution for building enterprise-scale applications.

- **Modules (NgModules)**: Containers for organizing application into cohesive blocks of functionality
- **Components**: Building blocks that control views (HTML templates) with TypeScript classes
- **Templates**: HTML views with Angular-specific syntax for data binding and directives
- **Services**: Reusable business logic and data access that can be injected into components
- **Dependency Injection (DI)**: Design pattern where classes receive dependencies from external sources
- **Directives**: Instructions that modify DOM structure or behavior
- **Pipes**: Transform data for display in templates
- **RxJS Observables**: Handle asynchronous operations and event streams
- **Two-way Data Binding**: Automatic synchronization between model and view
- **Change Detection**: Mechanism that tracks changes and updates the view

---

## Key Terms / Definitions

- **Decorator**: TypeScript feature that adds metadata to classes, methods, or properties (e.g., `@Component`)
- **Metadata**: Configuration information that tells Angular how to process a class
- **Provider**: Object that tells the injector how to obtain or create a dependency
- **Injector**: Service locator that creates dependencies and injects them into classes
- **Observable**: Lazy collections of multiple values over time (from RxJS library)
- **Zone.js**: Library that intercepts asynchronous operations for change detection
- **AOT (Ahead-of-Time)**: Compilation during build time for better performance
- **JIT (Just-in-Time)**: Compilation in the browser at runtime (development mode)
- **Standalone Components**: Components that don't require NgModule (Angular 14+)
- **Signal**: New reactive primitive for state management (Angular 16+)

---

## Project Structure

Angular CLI generates a structured project with organized folders. Understanding this structure helps navigate and maintain applications.
```
my-app/
├── src/
│   ├── app/
│   │   ├── components/         # Feature components
│   │   ├── services/           # Business logic services
│   │   ├── models/             # TypeScript interfaces/classes
│   │   ├── guards/             # Route guards
│   │   ├── pipes/              # Custom pipes
│   │   ├── directives/         # Custom directives
│   │   ├── app.component.ts    # Root component
│   │   ├── app.component.html  # Root template
│   │   ├── app.component.css   # Root styles
│   │   ├── app.config.ts       # App configuration (standalone)
│   │   └── app.routes.ts       # Route definitions
│   ├── assets/                 # Static files (images, etc.)
│   ├── environments/           # Environment configs
│   ├── index.html              # Main HTML file
│   ├── main.ts                 # Application entry point
│   └── styles.css              # Global styles
├── angular.json                # Angular CLI configuration
├── package.json                # Dependencies
└── tsconfig.json               # TypeScript configuration
```

---

## Component Structure

Components are the fundamental building blocks of Angular applications. Each component consists of a TypeScript class decorated with `@Component` and associated template and styles.

### Basic Component (Standalone)
```typescript
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-card',           // How to use in templates
  standalone: true,                     // No NgModule required (Angular 14+)
  imports: [CommonModule],              // Dependencies
  templateUrl: './user-card.component.html',  // External template
  // template: `<h1>{{ title }}</h1>`, // Inline template
  styleUrls: ['./user-card.component.css'],   // External styles
  // styles: [`h1 { color: blue; }`]   // Inline styles
})
export class UserCardComponent {
  // Properties (component state)
  title = 'User Card';
  count = 0;
  isActive = true;
  user = {
    name: 'John Doe',
    email: 'john@example.com'
  };
  
  // Methods
  increment(): void {
    this.count++;
  }
  
  handleClick(event: MouseEvent): void {
    console.log('Clicked', event);
  }
}
```

### Component with Lifecycle Hooks
```typescript
import { Component, OnInit, OnDestroy, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-lifecycle',
  standalone: true,
  template: `<p>Lifecycle Demo</p>`
})
export class LifecycleComponent implements OnInit, OnDestroy, OnChanges {
  
  ngOnChanges(changes: SimpleChanges): void {
    // Called when input properties change
    console.log('Changes detected', changes);
  }
  
  ngOnInit(): void {
    // Called once after component initialization
    // Good for: API calls, subscriptions setup
    console.log('Component initialized');
  }
  
  ngOnDestroy(): void {
    // Called before component is destroyed
    // Good for: cleanup (unsubscribe, clear timers)
    console.log('Component destroyed');
  }
  
  // Other lifecycle hooks:
  // ngDoCheck() - custom change detection
  // ngAfterContentInit() - after content projection
  // ngAfterContentChecked() - after content checked
  // ngAfterViewInit() - after view initialization
  // ngAfterViewChecked() - after view checked
}
```

---

## Template Syntax

Angular templates extend HTML with special syntax for data binding, directives, and event handling.

### Interpolation & Property Binding
```html
<!-- String interpolation -->
<h1>{{ title }}</h1>
<p>{{ count + 10 }}</p>
<p>{{ user.name.toUpperCase() }}</p>

<!-- Property binding -->
<img [src]="imageUrl" [alt]="imageAlt">
<button [disabled]="isDisabled">Click</button>
<div [class.active]="isActive">Active div</div>
<div [style.color]="textColor">Colored text</div>

<!-- Attribute binding (for attributes without DOM properties) -->
<button [attr.aria-label]="label">Button</button>
<td [attr.colspan]="colSpan">Cell</td>
```

**Difference**: Property binding sets DOM properties (JavaScript), attribute binding sets HTML attributes. Most cases use property binding.

### Event Binding
```html
<!-- Basic event -->
<button (click)="handleClick()">Click Me</button>

<!-- Event with $event object -->
<input (input)="handleInput($event)">
<button (click)="handleClick($event)">Click</button>

<!-- Multiple events -->
<div 
  (mouseenter)="onMouseEnter()" 
  (mouseleave)="onMouseLeave()">
  Hover me
</div>

<!-- Custom events from child components -->
<app-child (notify)="handleNotification($event)"></app-child>

<!-- Template reference variable -->
<input #nameInput>
<button (click)="greet(nameInput.value)">Greet</button>
```

### Two-Way Binding
```html
<!-- Two-way binding with [(ngModel)] -->
<input [(ngModel)]="name">
<p>Hello, {{ name }}!</p>

<!-- Expanded form (property + event) -->
<input 
  [ngModel]="name" 
  (ngModelChange)="name = $event">

<!-- Custom two-way binding -->
<app-counter [(count)]="counter"></app-counter>

<!-- In child component -->
@Input() count!: number;
@Output() countChange = new EventEmitter<number>();

updateCount(newCount: number) {
  this.count = newCount;
  this.countChange.emit(newCount);
}
```

**Note**: Requires `FormsModule` import for `ngModel`.

---

## Directives

Directives are instructions that modify the DOM structure or behavior.

### Structural Directives

Structural directives change the DOM layout by adding or removing elements. They use `*` prefix.
```html
<!-- *ngIf - conditional rendering -->
<p *ngIf="isVisible">Visible content</p>

<div *ngIf="user; else noUser">
  <p>Welcome, {{ user.name }}</p>
</div>
<ng-template #noUser>
  <p>No user logged in</p>
</ng-template>

<!-- *ngIf with as (store result) -->
<div *ngIf="user$ | async as user">
  {{ user.name }}
</div>

<!-- *ngFor - list rendering -->
<li *ngFor="let item of items">{{ item.name }}</li>

<!-- *ngFor with index and other variables -->
<li *ngFor="let item of items; let i = index; let first = first; let last = last">
  {{ i }}: {{ item.name }}
  <span *ngIf="first">(First)</span>
  <span *ngIf="last">(Last)</span>
</li>

<!-- *ngFor with trackBy for performance -->
<li *ngFor="let item of items; trackBy: trackByFn">
  {{ item.name }}
</li>

trackByFn(index: number, item: any): number {
  return item.id; // Unique identifier
}

<!-- *ngSwitch - multiple conditions -->
<div [ngSwitch]="status">
  <p *ngSwitchCase="'active'">Active</p>
  <p *ngSwitchCase="'inactive'">Inactive</p>
  <p *ngSwitchCase="'pending'">Pending</p>
  <p *ngSwitchDefault>Unknown</p>
</div>
```

### Attribute Directives

Attribute directives change the appearance or behavior of elements without changing DOM structure.
```html
<!-- ngClass - conditional classes -->
<div [ngClass]="{'active': isActive, 'disabled': isDisabled}">Content</div>
<div [ngClass]="statusClass">Content</div>
<div [ngClass]="['class1', 'class2']">Content</div>

<!-- ngStyle - conditional styles -->
<div [ngStyle]="{'color': textColor, 'font-size': fontSize + 'px'}">Text</div>
<div [ngStyle]="dynamicStyles">Text</div>

<!-- Built-in attribute directives -->
<input [ngModel]="name" (ngModelChange)="name = $event">
```

### Custom Directive
```typescript
import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[appHighlight]',
  standalone: true
})
export class HighlightDirective {
  @Input() appHighlight = 'yellow';  // Default color
  
  constructor(private el: ElementRef) {}
  
  @HostListener('mouseenter') onMouseEnter() {
    this.highlight(this.appHighlight);
  }
  
  @HostListener('mouseleave') onMouseLeave() {
    this.highlight('');
  }
  
  private highlight(color: string) {
    this.el.nativeElement.style.backgroundColor = color;
  }
}

// Usage
<p appHighlight="lightblue">Hover over me!</p>
```

---

## Component Communication

### Parent to Child (@Input)

Parent components pass data down to children via input properties. Children should not modify input values directly.
```typescript
// Child Component
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-child',
  standalone: true,
  template: `
    <h2>{{ title }}</h2>
    <p>Count: {{ count }}</p>
  `
})
export class ChildComponent {
  @Input() title!: string;           // Required input
  @Input() count: number = 0;        // Optional with default
  @Input({ required: true }) userId!: number;  // Explicitly required
  @Input({ alias: 'userName' }) name!: string; // Alias
}

// Parent Component
@Component({
  selector: 'app-parent',
  standalone: true,
  imports: [ChildComponent],
  template: `
    <app-child 
      [title]="parentTitle" 
      [count]="counter"
      [userId]="123"
      [userName]="'John'">
    </app-child>
  `
})
export class ParentComponent {
  parentTitle = 'Hello from Parent';
  counter = 42;
}
```

### Child to Parent (@Output)

Children emit events to notify parents of changes or actions. Parents listen to these events using event binding.
```typescript
// Child Component
import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-child',
  standalone: true,
  template: `
    <button (click)="sendNotification()">Notify Parent</button>
  `
})
export class ChildComponent {
  @Output() notify = new EventEmitter<string>();
  @Output() countChange = new EventEmitter<number>();
  
  sendNotification() {
    this.notify.emit('Hello from child!');
  }
  
  updateCount(newCount: number) {
    this.countChange.emit(newCount);
  }
}

// Parent Component
@Component({
  selector: 'app-parent',
  standalone: true,
  imports: [ChildComponent],
  template: `
    <app-child 
      (notify)="handleNotification($event)"
      (countChange)="counter = $event">
    </app-child>
    <p>{{ message }}</p>
  `
})
export class ParentComponent {
  message = '';
  counter = 0;
  
  handleNotification(data: string) {
    this.message = data;
  }
}
```

### ViewChild & ContentChild

Access child components or DOM elements from the parent component class.
```typescript
import { Component, ViewChild, AfterViewInit, ElementRef } from '@angular/core';

@Component({
  selector: 'app-parent',
  standalone: true,
  template: `
    <input #nameInput type="text">
    <app-child #childComp></app-child>
  `
})
export class ParentComponent implements AfterViewInit {
  // Access template reference variable
  @ViewChild('nameInput') nameInput!: ElementRef;
  
  // Access child component
  @ViewChild('childComp') childComponent!: ChildComponent;
  
  ngAfterViewInit() {
    // Available after view initialization
    console.log(this.nameInput.nativeElement.value);
    this.childComponent.someMethod();
  }
}
```

### Service-based Communication

Services provide a way to share data and logic across components that aren't directly related.
```typescript
// Shared Service
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'  // Singleton service
})
export class DataService {
  private messageSource = new BehaviorSubject<string>('default message');
  currentMessage$ = this.messageSource.asObservable();
  
  changeMessage(message: string) {
    this.messageSource.next(message);
  }
}

// Component A
@Component({
  selector: 'app-component-a',
  standalone: true,
  template: `<button (click)="sendMessage()">Send</button>`
})
export class ComponentA {
  constructor(private dataService: DataService) {}
  
  sendMessage() {
    this.dataService.changeMessage('Hello from A');
  }
}

// Component B
@Component({
  selector: 'app-component-b',
  standalone: true,
  template: `<p>{{ message$ | async }}</p>`
})
export class ComponentB {
  message$: Observable<string>;
  
  constructor(private dataService: DataService) {
    this.message$ = this.dataService.currentMessage$;
  }
}
```

---

## Services & Dependency Injection

Services contain reusable business logic, data access, and functionality that can be shared across components. Dependency Injection provides instances of services to classes that need them.

### Creating a Service
```typescript
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'  // Singleton - one instance for entire app
})
export class UserService {
  private apiUrl = 'https://api.example.com/users';
  
  // Inject HttpClient
  constructor(private http: HttpClient) {}
  
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }
  
  getUser(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }
  
  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }
  
  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, user);
  }
  
  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

interface User {
  id: number;
  name: string;
  email: string;
}
```

### Injecting a Service
```typescript
import { Component, OnInit } from '@angular/core';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-users',
  standalone: true,
  template: `
    <div *ngFor="let user of users">
      {{ user.name }}
    </div>
  `
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  
  // Inject service via constructor
  constructor(private userService: UserService) {}
  
  ngOnInit(): void {
    this.userService.getUsers().subscribe({
      next: (data) => this.users = data,
      error: (err) => console.error('Error:', err),
      complete: () => console.log('Request completed')
    });
  }
}
```

### Provider Scope
```typescript
// Root level - singleton for entire app
@Injectable({
  providedIn: 'root'
})
export class GlobalService {}

// Component level - new instance per component
@Component({
  selector: 'app-example',
  providers: [LocalService]  // New instance for this component
})
export class ExampleComponent {}

// Module level (if using modules)
@NgModule({
  providers: [ModuleScopedService]
})
export class FeatureModule {}
```

---

## Pipes

Pipes transform data for display in templates without changing the underlying data. Angular provides built-in pipes and allows custom pipes.

### Built-in Pipes
```html
<!-- DatePipe -->
<p>{{ today | date }}</p>
<p>{{ today | date:'short' }}</p>
<p>{{ today | date:'MM/dd/yyyy' }}</p>
<p>{{ today | date:'fullDate' }}</p>

<!-- UpperCasePipe / LowerCasePipe / TitleCasePipe -->
<p>{{ 'hello world' | uppercase }}</p>  <!-- HELLO WORLD -->
<p>{{ 'HELLO WORLD' | lowercase }}</p>  <!-- hello world -->
<p>{{ 'hello world' | titlecase }}</p>  <!-- Hello World -->

<!-- CurrencyPipe -->
<p>{{ 1234.56 | currency }}</p>              <!-- $1,234.56 -->
<p>{{ 1234.56 | currency:'EUR' }}</p>        <!-- €1,234.56 -->
<p>{{ 1234.56 | currency:'USD':'symbol':'1.0-0' }}</p>  <!-- $1,235 -->

<!-- DecimalPipe / PercentPipe -->
<p>{{ 3.14159 | number:'1.2-2' }}</p>    <!-- 3.14 (min 1 before, 2-2 after) -->
<p>{{ 0.25 | percent }}</p>              <!-- 25% -->

<!-- JsonPipe (for debugging) -->
<pre>{{ user | json }}</pre>

<!-- SlicePipe -->
<p>{{ 'Hello World' | slice:0:5 }}</p>   <!-- Hello -->
<p>{{ [1,2,3,4,5] | slice:1:3 | json }}</p>  <!-- [2,3] -->

<!-- AsyncPipe (subscribes to Observable/Promise) -->
<p>{{ user$ | async | json }}</p>
<div *ngIf="users$ | async as users">
  <li *ngFor="let user of users">{{ user.name }}</li>
</div>

<!-- Chaining pipes -->
<p>{{ today | date:'fullDate' | uppercase }}</p>
```

### Custom Pipe
```typescript
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'exponential',
  standalone: true
})
export class ExponentialPipe implements PipeTransform {
  transform(value: number, exponent: number = 1): number {
    return Math.pow(value, exponent);
  }
}

// Usage
<p>{{ 2 | exponential:3 }}</p>  <!-- 8 -->

// Example: Truncate pipe
@Pipe({
  name: 'truncate',
  standalone: true
})
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit: number = 10, trail: string = '...'): string {
    return value.length > limit ? value.substring(0, limit) + trail : value;
  }
}

// Usage
<p>{{ longText | truncate:20:'...' }}</p>
```

---

## Forms

Angular provides two approaches to handling forms: Template-driven (simpler, less code) and Reactive (more powerful, type-safe).

### Template-Driven Forms

Template-driven forms rely on directives in the template to create and manage form state. Easier for simple forms.
```typescript
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-template-form',
  standalone: true,
  imports: [FormsModule, CommonModule],
  template: `
    <form #userForm="ngForm" (ngSubmit)="onSubmit(userForm)">
      <!-- Text input -->
      <input 
        type="text" 
        name="username"
        [(ngModel)]="user.username"
        #username="ngModel"
        required
        minlength="3">
      
      <div *ngIf="username.invalid && username.touched">
        <small *ngIf="username.errors?.['required']">Username required</small>
        <small *ngIf="username.errors?.['minlength']">Min 3 characters</small>
      </div>
      
      <!-- Email input -->
      <input 
        type="email" 
        name="email"
        [(ngModel)]="user.email"
        #email="ngModel"
        required
        email>
      
      <div *ngIf="email.invalid && email.touched">
        <small *ngIf="email.errors?.['required']">Email required</small>
        <small *ngIf="email.errors?.['email']">Invalid email</small>
      </div>
      
      <!-- Select -->
      <select name="role" [(ngModel)]="user.role">
        <option value="user">User</option>
        <option value="admin">Admin</option>
      </select>
      
      <!-- Checkbox -->
      <input 
        type="checkbox" 
        name="agree"
        [(ngModel)]="user.agree"
        required>
      
      <button type="submit" [disabled]="userForm.invalid">Submit</button>
    </form>
    
    <pre>{{ user | json }}</pre>
  `
})
export class TemplateFormComponent {
  user = {
    username: '',
    email: '',
    role: 'user',
    agree: false
  };
  
  onSubmit(form: any) {
    if (form.valid) {
      console.log('Form submitted:', this.user);
    }
  }
}
```

### Reactive Forms

Reactive forms use explicit form control objects in the component class. Better for complex forms with dynamic validation.
```typescript
import { Component, OnInit } from '@angular/core';
import { 
  FormBuilder, 
  FormGroup, 
  FormControl,
  Validators,
  ReactiveFormsModule 
} from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reactive-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  template: `
    <form [formGroup]="userForm" (ngSubmit)="onSubmit()">
      <!-- Text input -->
      <input 
        type="text" 
        formControlName="username"
        placeholder="Username">
      
      <div *ngIf="username?.invalid && username?.touched">
        <small *ngIf="username?.errors?.['required']">Username required</small>
        <small *ngIf="username?.errors?.['minlength']">
          Min {{ username?.errors?.['minlength'].requiredLength }} characters
        </small>
      </div>
      
      <!-- Email -->
      <input 
        type="email" 
        formControlName="email"
        placeholder="Email">
      
      <div *ngIf="email?.invalid && email?.touched">
        <small *ngIf="email?.errors?.['required']">Email required</small>
        <small *ngIf="email?.errors?.['email']">Invalid email</small>
      </div>
      
      <!-- Password with custom validator -->
      <input 
        type="password" 
        formControlName="password"
        placeholder="Password">
      
      <!-- Nested FormGroup -->
      <div formGroupName="address">
        <input formControlName="street" placeholder="Street">
        <input formControlName="city" placeholder="City">
      </div>
      
      <!-- FormArray (dynamic controls) -->
      <div formArrayName="hobbies">
        <div *ngFor="let hobby of hobbies.controls; let i = index">
          <input [formControlName]="i" placeholder="Hobby">
          <button type="button" (click)="removeHobby(i)">Remove</button>
        </div>
      </div>
      <button type="button" (click)="addHobby()">Add Hobby</button>
      
      <button type="submit" [disabled]="userForm.invalid">Submit</button>
    </form>
    
    <pre>{{ userForm.value | json }}</pre>
  `
})
export class ReactiveFormComponent implements OnInit {
  userForm!: FormGroup;
  
  constructor(private fb: FormBuilder) {}
  
  ngOnInit() {
    this.userForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, this.strongPasswordValidator]],
      address: this.fb.group({
        street: [''],
        city: ['']
      }),
      hobbies: this.fb.array([])
    });
  }
  
  // Getters for easy access
  get username() {
    return this.userForm.get('username');
  }
  
  get email() {
    return this.userForm.get('email');
  }
  
  get hobbies() {
    return this.userForm.get('hobbies') as FormArray;
  }
  
  // Custom validator
  strongPasswordValidator(control: FormControl) {
    const value = control.value || '';
    const hasNumber = /[0-9]/.test(value);
    const hasUpper = /[A-Z]/.test(value);
    const hasLower = /[a-z]/.test(value);
    const valid = hasNumber && hasUpper && hasLower && value.length >= 8;
    return valid ? null : { weakPassword: true };
  }
  
  addHobby() {
    this.hobbies.push(this.fb.control(''));
  }
  
  removeHobby(index: number) {
    this.hobbies.removeAt(index);
  }
  
  onSubmit() {
    if (this.userForm.valid) {
      console.log('Form submitted:', this.userForm.value);
      this.userForm.reset();
    }
  }
}
```

---

## HTTP Client

Angular's HttpClient provides a simplified API for HTTP requests with observables, interceptors, and typed responses.

### Setup & Basic Usage
```typescript
// main.ts or app.config.ts
import { provideHttpClient } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient()
  ]
};

// Service
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'https://api.example.com';
  
  constructor(private http: HttpClient) {}
  
  // GET request
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/users`)
      .pipe(
        retry(2),  // Retry failed request twice
        catchError(this.handleError)
      );
  }
  
  // GET with params
  searchUsers(query: string): Observable<User[]> {
    const params = new HttpParams()
      .set('q', query)
      .set('limit', '10');
    
    return this.http.get<User[]>(`${this.apiUrl}/users`, { params });
  }
  
  // GET single item
  getUser(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/users/${id}`);
  }
  
  // POST request
  createUser(user: User): Observable<User> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    
    return this.http.post<User>(`${this.apiUrl}/users`, user, { headers })
      .pipe(catchError(this.handleError));
  }
  
  // PUT request
  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/users/${id}`, user)
      .pipe(catchError(this.handleError));
  }
  
  // PATCH request
  patchUser(id: number, updates: Partial<User>): Observable<User> {
    return this.http.patch<User>(`${this.apiUrl}/users/${id}`, updates);
  }
  
  // DELETE request
  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/users/${id}`)
      .pipe(catchError(this.handleError));
  }
  
  // Transform response
  getUserNames(): Observable<string[]> {
    return this.http.get<User[]>(`${this.apiUrl}/users`)
      .pipe(
        map(users => users.map(u => u.name))
      );
  }
  
  private handleError(error: any) {
    console.error('API Error:', error);
    return throwError(() => new Error('Something went wrong'));
  }
}
```

### HTTP Interceptors

Interceptors intercept HTTP requests and responses for cross-cutting concerns like authentication, logging, or error handling.
```typescript
import { HttpInterceptorFn } from '@angular/common/http';
// Functional interceptor (Angular 15+)
export const authInterceptor: HttpInterceptorFn = (req, next) => {
const token = localStorage.getItem('token');
if (token) {
const cloned = req.clone({
headers: req.headers.set('Authorization', Bearer ${token})
});
return next(cloned);
}
return next(req);
};
// Logging interceptor
export const loggingInterceptor: HttpInterceptorFn = (req, next) => {
console.log('Request:', req.method, req.url);
const start = Date.now();
return next(req).pipe(
tap(event => {
if (event.type === HttpEventType.Response) {
const elapsed = Date.now() - start;
console.log(Response in ${elapsed}ms:, event.status);
}
})
);
};
// Register in app.config.ts
import { provideHttpClient, withInterceptors } from '@angular/common/http';
export const appConfig: ApplicationConfig = {
providers: [
provideHttpClient(
withInterceptors([authInterceptor, loggingInterceptor])
)
]
};
```
---

## Routing

Angular Router enables navigation between views based on URL changes, with support for nested routes, guards, and lazy loading.

### Basic Routing Setup
```typescript
// app.routes.ts
import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { UserComponent } from './user/user.component';
import { NotFoundComponent } from './not-found/not-found.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },  // Default route
  { path: 'about', component: AboutComponent },
  { path: 'user/:id', component: UserComponent },  // Route parameter
  { path: 'admin', loadChildren: () => import('./admin/admin.routes').then(m => m.ADMIN_ROUTES) },  // Lazy load
  { path: '**', component: NotFoundComponent }  // 404 catch-all
];

// main.ts
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes)
  ]
});

// app.component.ts
import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  template: `
    <nav>
      <a routerLink="/" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">Home</a>
      <a routerLink="/about" routerLinkActive="active">About</a>
      <a [routerLink]="['/user', userId]">User</a>
    </nav>
    <router-outlet></router-outlet>
  `
})
export class AppComponent {
  userId = 123;
}
```

### Route Parameters & Query Params
```typescript
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-user',
  standalone: true,
  template: `
    <h1>User {{ userId }}</h1>
    <p>Tab: {{ tab }}</p>
  `
})
export class UserComponent implements OnInit {
  userId!: number;
  tab: string = '';
  
  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}
  
  ngOnInit() {
    // Route parameters (e.g., /user/123)
    this.route.params.subscribe(params => {
      this.userId = +params['id'];  // + converts to number
    });
    
    // Or using snapshot (for non-changing params)
    this.userId = +this.route.snapshot.params['id'];
    
    // Query parameters (e.g., /user/123?tab=profile)
    this.route.queryParams.subscribe(params => {
      this.tab = params['tab'] || 'overview';
    });
    
    // Or using snapshot
    this.tab = this.route.snapshot.queryParams['tab'];
  }
  
  navigateToEdit() {
    // Programmatic navigation
    this.router.navigate(['/user', this.userId, 'edit']);
    
    // With query params
    this.router.navigate(['/user', this.userId], {
      queryParams: { tab: 'settings' }
    });
    
    // Relative navigation
    this.router.navigate(['../other'], { relativeTo: this.route });
  }
}
```

### Route Guards

Guards control access to routes based on conditions like authentication or unsaved changes.
```typescript
// auth.guard.ts
import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from './auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  
  if (authService.isLoggedIn()) {
    return true;
  }
  
  // Redirect to login
  return router.createUrlTree(['/login'], {
    queryParams: { returnUrl: state.url }
  });
};

// can-deactivate.guard.ts
export interface CanComponentDeactivate {
  canDeactivate: () => boolean | Observable<boolean>;
}

export const canDeactivateGuard: CanDeactivateFn<CanComponentDeactivate> = (component) => {
  return component.canDeactivate ? component.canDeactivate() : true;
};

// routes with guards
export const routes: Routes = [
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [authGuard]  // Check before entering
  },
  {
    path: 'edit',
    component: EditComponent,
    canDeactivate: [canDeactivateGuard]  // Check before leaving
  }
];

// Component implementing CanDeactivate
export class EditComponent implements CanComponentDeactivate {
  hasUnsavedChanges = false;
  
  canDeactivate(): boolean {
    if (this.hasUnsavedChanges) {
      return confirm('You have unsaved changes. Do you want to leave?');
    }
    return true;
  }
}
```

### Nested Routes & Child Routes
```typescript
export const routes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      { path: '', component: DashboardHomeComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'settings', component: SettingsComponent }
    ]
  }
];

// DashboardComponent template
@Component({
  template: `
    <h1>Dashboard</h1>
    <nav>
      <a routerLink="profile">Profile</a>
      <a routerLink="settings">Settings</a>
    </nav>
    <router-outlet></router-outlet>  <!-- Child routes render here -->
  `
})
export class DashboardComponent {}
```

---

## RxJS & Observables

Angular heavily uses RxJS Observables for asynchronous operations. Observables are lazy collections of values over time.

### Basic Observable Usage
```typescript
import { Observable, of, from, interval } from 'rxjs';
import { map, filter, tap, catchError } from 'rxjs/operators';

// Create observables
const obs1$ = of(1, 2, 3);  // Emit values synchronously
const obs2$ = from([1, 2, 3]);  // From array
const obs3$ = interval(1000);  // Emit every second

// Subscribe to observable
obs1$.subscribe({
  next: (value) => console.log('Value:', value),
  error: (err) => console.error('Error:', err),
  complete: () => console.log('Complete')
});

// Operators transform observable streams
const doubled$ = obs1$.pipe(
  map(x => x * 2),
  filter(x => x > 2)
);
```

### Common Operators
```typescript
import { 
  map, filter, tap, 
  catchError, retry,
  debounceTime, distinctUntilChanged,
  switchMap, mergeMap, concatMap,
  combineLatest, forkJoin
} from 'rxjs/operators';

// map - transform values
this.http.get<User[]>('/users').pipe(
  map(users => users.map(u => u.name))
);

// filter - only emit matching values
interval(1000).pipe(
  filter(x => x % 2 === 0)  // Only even numbers
);

// tap - side effects (logging, debugging)
this.http.get('/data').pipe(
  tap(data => console.log('Data received:', data))
);

// catchError - handle errors
this.http.get('/data').pipe(
  catchError(err => {
    console.error('Error:', err);
    return of([]); // Return fallback value
  })
);

// debounceTime - wait before emitting
searchControl.valueChanges.pipe(
  debounceTime(300),  // Wait 300ms after typing stops
  distinctUntilChanged()  // Only if value changed
);

// switchMap - cancel previous, start new
searchControl.valueChanges.pipe(
  debounceTime(300),
  switchMap(query => this.apiService.search(query))
);

// combineLatest - combine multiple observables
combineLatest([user$, settings$]).pipe(
  map(([user, settings]) => ({ user, settings }))
);

// forkJoin - wait for all to complete
forkJoin([
  this.http.get('/users'),
  this.http.get('/posts')
]).subscribe(([users, posts]) => {
  // Both completed
});
```

### Subjects

Subjects are both Observable and Observer - they can multicast to multiple subscribers.
```typescript
import { Subject, BehaviorSubject, ReplaySubject } from 'rxjs';

// Subject - no initial value, no replay
const subject = new Subject<number>();
subject.next(1);
subject.subscribe(x => console.log('A:', x));  // Won't receive 1
subject.next(2);  // A: 2

// BehaviorSubject - has current value
const behavior = new BehaviorSubject<number>(0);  // Initial value
behavior.subscribe(x => console.log('B:', x));  // B: 0 (immediate)
behavior.next(1);  // B: 1

// ReplaySubject - replays N last values
const replay = new ReplaySubject<number>(2);  // Replay last 2
replay.next(1);
replay.next(2);
replay.next(3);
replay.subscribe(x => console.log('R:', x));  // R: 2, R: 3
```

### Async Pipe

The async pipe subscribes to observables in templates and automatically unsubscribes when component is destroyed.
```typescript
@Component({
  template: `
    <!-- Subscribe to observable -->
    <div *ngIf="user$ | async as user">
      {{ user.name }}
    </div>
    
    <!-- Handle loading and error states -->
    <div *ngIf="loading">Loading...</div>
    <div *ngIf="error">{{ error }}</div>
    <ul *ngIf="users$ | async as users">
      <li *ngFor="let user of users">{{ user.name }}</li>
    </ul>
  `
})
export class UsersComponent {
  users$: Observable<User[]>;
  user$: Observable<User>;
  loading = false;
  error: string | null = null;
  
  constructor(private userService: UserService) {
    this.users$ = this.userService.getUsers();
    this.user$ = this.userService.getUser(1);
  }
}
```

---

## Signals (Angular 16+)

Signals are a new reactive primitive for fine-grained reactivity and better performance. They provide an alternative to Observables for synchronous state.

### Basic Signal Usage
```typescript
import { Component, signal, computed, effect } from '@angular/core';

@Component({
  selector: 'app-counter',
  standalone: true,
  template: `
    <p>Count: {{ count() }}</p>
    <p>Double: {{ doubled() }}</p>
    <button (click)="increment()">+</button>
    <button (click)="decrement()">-</button>
    <button (click)="reset()">Reset</button>
  `
})
export class CounterComponent {
  // Writable signal
  count = signal(0);
  
  // Computed signal (derived state)
  doubled = computed(() => this.count() * 2);
  
  // Effect (side effects when signals change)
  constructor() {
    effect(() => {
      console.log('Count changed to:', this.count());
    });
  }
  
  increment() {
    this.count.update(c => c + 1);
    // or this.count.set(this.count() + 1);
  }
  
  decrement() {
    this.count.update(c => c - 1);
  }
  
  reset() {
    this.count.set(0);
  }
}
```

### Signal-based Components
```typescript
import { Component, signal, computed, input, output } from '@angular/core';

@Component({
  selector: 'app-user-card',
  standalone: true,
  template: `
    <div class="card">
      <h2>{{ fullName() }}</h2>
      <p>Age: {{ age() }}</p>
      <p>Status: {{ status() }}</p>
      <button (click)="notify()">Notify</button>
    </div>
  `
})
export class UserCardComponent {
  // Input signals (Angular 17.1+)
  firstName = input.required<string>();
  lastName = input<string>('');
  age = input<number>(0);
  
  // Output signals
  userClicked = output<string>();
  
  // Computed values
  fullName = computed(() => `${this.firstName()} ${this.lastName()}`);
  status = computed(() => this.age() >= 18 ? 'Adult' : 'Minor');
  
  notify() {
    this.userClicked.emit(this.fullName());
  }
}

// Usage
<app-user-card 
  [firstName]="'John'" 
  [lastName]="'Doe'"
  [age]="25"
  (userClicked)="handleClick($event)">
</app-user-card>
```

---

## Best Practices

- **Use Standalone Components**: Modern approach without NgModules (Angular 14+)
- **Prefer Signals over RxJS for synchronous state**: Better performance and simpler code
- **Use OnPush change detection**: Improves performance by reducing unnecessary checks
- **Implement trackBy in *ngFor**: Prevents unnecessary DOM re-renders when data changes
- **Unsubscribe from Observables**: Use `takeUntil`, `async` pipe, or `takeUntilDestroyed()` to prevent memory leaks
- **Use Reactive Forms for complex scenarios**: Better type safety and validation control
- **Lazy load feature modules**: Reduces initial bundle size
- **Use services for business logic**: Keep components focused on presentation
- **Follow naming conventions**: 
  - Components: `user-card.component.ts`
  - Services: `user.service.ts`
  - Pipes: `truncate.pipe.ts`
- **Use strict TypeScript**: Enable `strict` mode in `tsconfig.json`
- **Avoid logic in templates**: Keep templates simple, move complex logic to component class
- **Use guard functions instead of classes**: Simpler and more functional (Angular 15+)
- **Inject dependencies via constructor**: Angular's dependency injection pattern
- **Use providedIn: 'root' for services**: Creates singletons automatically
- **Keep components small**: Single responsibility principle
- **Use readonly for signals**: Expose computed signals as readonly when appropriate

---

## Common Mistakes

### Not unsubscribing from Observables
```typescript
// ❌ Wrong - memory leak
export class Component implements OnInit {
  ngOnInit() {
    this.service.getData().subscribe(data => {
      this.data = data;
    });
  }
}

// ✅ Correct - using async pipe (auto unsubscribes)
export class Component {
  data$ = this.service.getData();
}

// ✅ Correct - using takeUntilDestroyed (Angular 16+)
export class Component implements OnInit {
  private destroyRef = inject(DestroyRef);
  
  ngOnInit() {
    this.service.getData()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(data => this.data = data);
  }
}
```

### Mutating input properties
```typescript
// ❌ Wrong
export class ChildComponent {
  @Input() user!: User;
  
  updateUser() {
    this.user.name = 'New Name';  // Mutates parent's data
  }
}

// ✅ Correct - emit event
export class ChildComponent {
  @Input() user!: User;
  @Output() userChange = new EventEmitter<User>();
  
  updateUser() {
    const updated = { ...this.user, name: 'New Name' };
    this.userChange.emit(updated);
  }
}
```

### Missing trackBy in *ngFor
```typescript
// ❌ Wrong - recreates all DOM elements on data change
<li *ngFor="let item of items">{{ item.name }}</li>

// ✅ Correct
<li *ngFor="let item of items; trackBy: trackById">{{ item.name }}</li>

trackById(index: number, item: any): number {
  return item.id;
}
```

### Subscribing in subscribe (nested subscriptions)
```typescript
// ❌ Wrong - nested subscriptions
this.service1.getData().subscribe(data1 => {
  this.service2.getData(data1.id).subscribe(data2 => {
    this.result = data2;
  });
});

// ✅ Correct - using switchMap
this.service1.getData().pipe(
  switchMap(data1 => this.service2.getData(data1.id))
).subscribe(data2 => {
  this.result = data2;
});
```

### Forgetting to import required modules
```typescript
// ❌ Wrong - FormsModule not imported
<input [(ngModel)]="name">  // Error: Can't bind to 'ngModel'

// ✅ Correct
@Component({
  imports: [FormsModule],  // Import required module
  template: `<input [(ngModel)]="name">`
})
```

### Using *ngIf and *ngFor on same element
```typescript
// ❌ Wrong - two structural directives on one element
<div *ngIf="show" *ngFor="let item of items">{{ item }}</div>

// ✅ Correct - wrap with ng-container
<ng-container *ngIf="show">
  <div *ngFor="let item of items">{{ item }}</div>
</ng-container>
```

### Not handling HTTP errors
```typescript
// ❌ Wrong - no error handling
this.http.get('/data').subscribe(data => {
  this.data = data;
});

// ✅ Correct
this.http.get('/data').pipe(
  catchError(err => {
    console.error('Error:', err);
    this.errorMessage = 'Failed to load data';
    return of([]);  // Return empty array as fallback
  })
).subscribe(data => {
  this.data = data;
});
```

---

## Quick Reference

### Component Decorators

| Decorator | Purpose |
|-----------|---------|
| `@Component` | Define component metadata |
| `@Input()` | Receive data from parent |
| `@Output()` | Emit events to parent |
| `@ViewChild()` | Query view DOM element/component |
| `@ViewChildren()` | Query multiple view elements |
| `@ContentChild()` | Query projected content |
| `@HostListener()` | Listen to host element events |
| `@HostBinding()` | Bind to host element properties |

### Lifecycle Hooks

| Hook | When Called |
|------|-------------|
| `ngOnChanges()` | When input properties change |
| `ngOnInit()` | Once after first `ngOnChanges()` |
| `ngDoCheck()` | Every change detection cycle |
| `ngAfterContentInit()` | After content projection |
| `ngAfterContentChecked()` | After content checked |
| `ngAfterViewInit()` | After view initialization |
| `ngAfterViewChecked()` | After view checked |
| `ngOnDestroy()` | Before component destruction |

### Common Directives

| Directive | Purpose |
|-----------|---------|
| `*ngIf` | Conditional rendering |
| `*ngFor` | List rendering |
| `*ngSwitch` | Multiple conditional rendering |
| `[ngClass]` | Dynamic CSS classes |
| `[ngStyle]` | Dynamic inline styles |
| `[(ngModel)]` | Two-way data binding |
| `[ngValue]` | Bind value to select options |

### RxJS Operators

| Operator | Purpose |
|----------|---------|
| `map` | Transform emitted values |
| `filter` | Emit only matching values |
| `tap` | Side effects without changing stream |
| `catchError` | Handle errors |
| `switchMap` | Switch to new observable |
| `mergeMap` | Merge concurrent observables |
| `debounceTime` | Wait before emitting |
| `distinctUntilChanged` | Emit only when value changes |
| `take` | Take first N emissions |
| `takeUntil` | Take until another observable emits |

### CLI Commands
```bash
# Create new app
ng new my-app

# Generate components
ng generate component my-component
ng g c my-component --standalone

# Generate service
ng generate service my-service
ng g s my-service

# Generate pipe/directive/guard
ng g pipe my-pipe
ng g directive my-directive
ng g guard my-guard

# Serve app
ng serve
ng serve --open --port 4300

# Build for production
ng build
ng build --configuration production

# Run tests
ng test
ng e2e

# Update Angular
ng update @angular/cli @angular/core
```

