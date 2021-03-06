/**
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.furnace.container.simple;

import java.lang.annotation.Annotation;

import org.jboss.forge.furnace.addons.Addon;
import org.jboss.forge.furnace.container.simple.lifecycle.SimpleContainer;
import org.jboss.forge.furnace.event.PostStartup;
import org.jboss.forge.furnace.event.PreShutdown;

/**
 * An abstract adapter class for receiving container events. The methods in this class are empty. This class exists as a
 * convenience for creating listener objects.
 * 
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
public abstract class AbstractEventListener implements EventListener
{
   @Override
   public void handleEvent(Object event, Annotation... qualifiers)
   {
      Addon currentAddon = SimpleContainer.getAddon(getClass().getClassLoader());
      if (event instanceof PostStartup)
      {
         PostStartup postStartup = (PostStartup) event;
         if (currentAddon.equals(postStartup.getAddon()))
         {
            handleThisPostStartup();
         }
         else
         {
            handleEvent(postStartup);
         }
      }
      else if (event instanceof PreShutdown)
      {
         PreShutdown preShutdown = (PreShutdown) event;
         if (currentAddon.equals(preShutdown.getAddon()))
         {
            handleThisPreShutdown();
         }
         else
         {
            handleEvent(preShutdown);
         }
      }
      else
      {
         handleCustomEvent(event, qualifiers);
      }
   }

   /**
    * This method is called when a {@link PostStartup} event is fired to the {@link Addon} in which this listener is
    * registered.
    */
   protected void handleThisPostStartup()
   {
      // Do nothing
   }

   /**
    * This method is called when a {@link PreShutdown} event is fired for the {@link Addon} in which this listener is
    * registered
    */
   protected void handleThisPreShutdown()
   {
      // Do nothing
   }

   /**
    * This method is called when a {@link PreShutdown} event is fired
    * 
    * @param preShutdown the {@link PreShutdown} event
    */
   protected void handleEvent(PreShutdown preShutdown)
   {
      // Do nothing
   }

   /**
    * This method is called when a {@link PostStartup} event is fired
    * 
    * @param postStartup the {@link PostStartup} event
    */
   protected void handleEvent(PostStartup postStartup)
   {
      // Do nothing
   }

   /**
    * This method is called when the event is neither a {@link PostStartup} nor a {@link PreShutdown} event.
    * 
    * @param event the event to handle
    * @param qualifiers the qualifiers, if any
    */
   protected void handleCustomEvent(Object event, Annotation... qualifiers)
   {
      // Do nothing
   }
}
