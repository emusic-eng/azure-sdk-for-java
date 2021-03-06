/**
 * Copyright Microsoft Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.microsoft.windowsazure.services.serviceBus.implementation;

import static com.microsoft.windowsazure.services.core.utils.ExportUtils.*;

import java.net.URISyntaxException;
import java.util.Map;

import com.microsoft.windowsazure.services.core.Builder;
import com.microsoft.windowsazure.services.core.utils.ConnectionStringSyntaxException;
import com.microsoft.windowsazure.services.serviceBus.ServiceBusConfiguration;

public class Exports implements Builder.Exports {

    @Override
    public void register(Builder.Registry registry) {
        registry.add(WrapContract.class, WrapRestProxy.class);
        registry.add(WrapTokenManager.class);
        registry.add(WrapFilter.class);

        registry.add(new Builder.Factory<ServiceBusConnectionSettings>() {

            @Override
            public <S> ServiceBusConnectionSettings create(String profile, Class<S> service, Builder builder,
                    Map<String, Object> properties) {
                try {
                    return new ServiceBusConnectionSettings((String) getPropertyIfExists(profile, properties,
                            ServiceBusConfiguration.CONNECTION_STRING), (String) getPropertyIfExists(profile,
                            properties, ServiceBusConfiguration.URI), (String) getPropertyIfExists(profile, properties,
                            ServiceBusConfiguration.WRAP_URI), (String) getPropertyIfExists(profile, properties,
                            ServiceBusConfiguration.WRAP_NAME), (String) getPropertyIfExists(profile, properties,
                            ServiceBusConfiguration.WRAP_PASSWORD));
                }
                catch (ConnectionStringSyntaxException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
                catch (URISyntaxException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        });
    }
}
