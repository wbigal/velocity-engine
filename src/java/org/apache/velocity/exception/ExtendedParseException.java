package org.apache.velocity.exception;

/*
 * Copyright 2000-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * All Exceptions that can provide additional information about the place
 * where the error happened (template name, column and line number) can
 * implement this interface and the ParseErrorException will then be able
 * to deal with this information.
 *
 * @author <a href="hps@intermeta.de">Henning P. Schmiedehausen</a>
 * @version $Id$
 */
public interface ExtendedParseException
{
    /**
     * returns the Template name where this exception occured.
     * @return The Template name where this exception occured.
     */
    String getTemplateName();

    /**
     * returns the line number where this exception occured.
     * @return The line number where this exception occured.
     */
    int getLineNumber();

    /**
     * returns the column number where this exception occured.
     * @return The column number where this exception occured.
     */
    int getColumnNumber();
}
