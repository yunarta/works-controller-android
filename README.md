# works-controller-android

Works Controller Android is a library that will greatly help you dealing with Android Activity and 
Fragment lifecycle with ease.

One of the bane on Android programming is that you need to deal with device orientation change that
will restart either your Activity or Fragments, and especially if you are doing asynchronous task on
the front end.

## Simplifies Lifecycle

In very basic, as long your Activity in still in task stacks or your Fragment is fragment stacks,
even tough they are recreated during device orientation, the controller are not.

So yes, you can even run your asynchronous task without worrying that the host which is the Activity
or Fragment gets recreated. And we also provides you with a way to make sure the result will be delivered
to the correct host.

You can read more detail on how the [Controller Lifecycle](https://github.com/mobilesolutionworks/works-controller-android/wiki/Controller-Lifecycle)
 
## Creating Controller
Below is the snippet to create a controller, the controller manager will only call the callback and
then creates the controller implementation only when the controller for the specified id has not been
created yet, otherwise it will immediately returns the previous created instance.

```java
WorksControllerImpl controller = getControllerManager().initController(0, null, new WorksControllerManager.ControllerCallbacks<WorksControllerImpl>() {
    @Override
    public WorksControllerImpl onCreateController(int id, Bundle args) {
        return new WorksControllerImpl();
    }
});
```

## Solving Problems
 
### Persistent container
As controller are not recreated during host recreation, you can persist data such as Adapter that takes
time to load. 

```java
public class WorksControllerImpl extends WorksController {
    
    Adapter mAdapter;
    
    @Override
    public void onCreate(Bundle arguments) {
            mAdapter = // ... this creates adapter 
    }
    
    public Adapter getAdapter() { return mAdapter; } 
}

public class ActivityImpl extends WorksActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WorksControllerImpl controller = // ... acquires the controller
        
        ListView listView = // ... get your list view here
        listView.setAdapter(controller.getAdapter());
    }
} 

```

### User Interface container readiness 
In Android, there are certain state where the user interface is not ready for changes primarily because
it is still in paused state. 

This behavior can be observe by running the example "Fragment Transaction Demo" and choose setResult #1.

In the demonstration, the flow are as such below:
1. A fragment A call startActivityForResult
2. When onActivityResult received, show the result in current fragment A
3. Push current fragment A to back stack, and push a new fragment B
4. When the fragment is popped, you will see changes on fragment A is not working


```java
controller.runWhenUiIsReady(new Runnable() {
    
    public void run() {
        // the codes here will be executed when the UI context is ready
    }
})
```

## Using Controller in your project

Add this line in your `dependencies` section in your build.gradle
```groovy
compile 'com.mobilesolutionworks:controller-core:1.1.0'
```

### Start using Controller
Controller is a drop in solution to your existing project if you are using Android support library for
activities and fragments.
 
1. Change the activity or fragment base class 
   - Change your fragments base class from Android support Fragment into WorksFragment
   - Change your activity base class from Android support AppCompatActivity into WorksActivity
2. Then you can use controller in your project right away

### Adding Controller functionality
Controller can be added to your existing project as well, if you are bound to use third party base Activity
or Fragment.

Simple solution
1. Create a copy of WorksActivity and WorksFragment in your project with different package
2. Make WorksActivity and WorksFragment subclass of your third party base classes, or you can copy the content
of WorksActivity and WorksFragment into your base classes as well
3. Then you can use controller in your project right away
      