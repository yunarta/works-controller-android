# Works Controller for Android
[![Build Status](http://jenkins.mobilesolutionworks.com:8080/job/github/job/mobilesolutionworks/job/works-controller-android/job/master/badge/icon)](http://jenkins.mobilesolutionworks.com:8080/job/github/job/mobilesolutionworks/job/works-controller-android/job/master/)
[![codecov](https://codecov.io/gh/mobilesolutionworks/works-controller-android/branch/master/graph/badge.svg)](https://codecov.io/gh/mobilesolutionworks/works-controller-android)

Works Controller Android is a library that will greatly help you dealing with Android Activity and 
Fragment lifecycle with ease.

One of the bane on Android programming is that you need to deal with device orientation change that
will restart either your Activity or Fragments, and especially if you are doing asynchronous task on
the front end.

The other one is the situation where Android runs your callback in paused stated such as during 
onActivityResult() leading to complexity to make sure the result is properly rendered.

## Getting Started
Below is the snippet to create a controller, the controller manager will only call the callback and
then creates the controller implementation only when the controller for the specified id has not been
created yet, otherwise it will immediately returns the previous created instance.

```java
public class WorksControllerImpl extends WorksController {
    
}

public class WorksFragmentImpl extends WorksFragment
{
    @Override
    public onCreate(Bundle savedInstanceState) {}
        WorksControllerImpl controller = getControllerManager().initController(0, null, new WorksControllerManager.ControllerCallbacks<WorksControllerImpl>() {
            @Override
            public WorksControllerImpl onCreateController(int id, Bundle args) {
                return new WorksControllerImpl();
            }
        });
    }
}
```

From the snippet above, you can start testing and observing that WorksController implementation is not
recreated, and from there you can start doing more asynchronous stuff that is previously difficult to be 
done with Fragments alone.

## Solving Problems

In very basic, as long your Activity in still in task stacks or your Fragment is fragment stacks,
even tough they are recreated during device orientation, the controller are not.

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

        // when device is rotate, this will return previous controller
        WorksControllerImpl controller = getControllerManager().initController(0, null, new WorksControllerManager.ControllerCallbacks<WorksControllerImpl>() {
             @Override
             public WorksControllerImpl onCreateController(int id, Bundle args) {
                 return new WorksControllerImpl();
             }
         });

        ListView listView = // ... get your list view here
        listView.setAdapter(controller.getAdapter());
    }
} 
```

### Asynchronous operation
So yes, you can even run your asynchronous task without worrying that the host which is the Activity
or Fragment gets recreated. And we also provides you with a way to make sure the result will be delivered
to the correct host.

You can read more detail on how the [Controller Lifecycle](https://github.com/mobilesolutionworks/works-controller-android/wiki/Controller-Lifecycle) works.

```java
public class WorksControllerImpl extends WorksController {
    
    BaseAdapter mAdapter;
    
    AsyncTask mAsyncTask;
    
    @Override
    public void onCreate(Bundle arguments) {
        mAdapter = // ... this creates adapter
        mAsyncTask = new AsyncTask<String, String, String>() {
            
            protected String doInBackground(String ... params) {
                // load the data from network
                return string;
            }
            
            protected void onPostExecute(String result) {
                // reset the data and call notify data set changed
                mAdapter.resetDataFromString(result);
                mAdapter.notifyDataSetChanged();
            }
        }; 
    }
    
    public Adapter getAdapter() { return mAdapter; } 
}

public class ActivityImpl extends WorksActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // when device is rotate, this will return previous controller
        WorksControllerImpl controller = getControllerManager().initController(0, null, new WorksControllerManager.ControllerCallbacks<WorksControllerImpl>() {
             @Override
             public WorksControllerImpl onCreateController(int id, Bundle args) {
                 return new WorksControllerImpl();
             }
         });

        ListView listView = // ... get your list view here
        
        // 1. if the async task above is not finished, the adapter show empty data
        // 2. if the async task is finished during rotation, after rotation we will set the adapter again. 
        listView.setAdapter(controller.getAdapter());
    }
} 
```

You can read get more complex async operation example on [here](https://github.com/mobilesolutionworks/works-controller-android/wiki/Async-Operation-Example)

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
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == Activity.RESULT_OK) {
        // in this state, the fragment or activity is still in paused state
        controller.runWhenUiIsReady(new Runnable() {
            
            public void run() {
                // the codes here will be executed when the UI context is ready
            }
        });
    }
}
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

## Running TEst

In order to run the test properly, you need to install [Test Butler](https://github.com/linkedin/test-butler) into your simulator since the unit test will test rotating the device programmatically.
