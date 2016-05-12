/**
 *  Copyright 2005-2015 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.talend.jms.copy;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

/**
 * Example of the "main class". Put your bootstrap logic here.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        CopyCommand copy = new CopyCommand();
        JCommander jc = new JCommander(copy);
        try {
            jc.parse(args);
            ActiveMQConnectionFactory sourceCF = new ActiveMQConnectionFactory(copy.user, copy.password, copy.surl);
            sourceCF.setAlwaysSessionAsync(false);
            sourceCF.setOptimizeAcknowledge(true);
            ActiveMQConnectionFactory destCF = new ActiveMQConnectionFactory(copy.user, copy.password, copy.durl);
            destCF.setOptimizeAcknowledge(true);
            new Copy(sourceCF, destCF).copyQueues(copy.filter);
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            jc.usage();
        }
    }

}
