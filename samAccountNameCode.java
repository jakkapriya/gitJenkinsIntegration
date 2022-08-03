   /// <summary>

        /// Generates an available samacountname string based on first name and last name. This method checks if the samaccountname already exists using the injected ILDAP and IActiveDirectory implementation instances.

        /// </summary>

        /// <param name="firstName"></param>

        /// <param name="lastName"></param>

        /// <returns></returns>
class main {
        public string GenerateLoginId(string firstName, string lastName)

        {

            byte maxSamAccountNameLength = 20;

           

            if (string.IsNullOrWhiteSpace(firstName))

            {

                throw new ArgumentException("cannot be null or whitespace.", nameof(firstName));

            }

 

            if (string.IsNullOrWhiteSpace(lastName))

            {

                throw new ArgumentException(" cannot be null or whitespace.", nameof(lastName));

            }

 

            lastName = lastName.FoldToASCII().RemoveSpecialCharacters();

            firstName = firstName.FoldToASCII().RemoveSpecialCharacters();

 

            bool samAccountNameExists = true;

            bool exceedsMaxLength = false;

            string samAccountName = null;

 

            // generate username using lastname + first char of first name. Use more characters of firstname if the accountname already exists.

            // make sure samaccountname does not exceed 20 characters.

            for (int i = 1; samAccountNameExists && i <= firstName.Length && !exceedsMaxLength; i++)

            {

                samAccountName = "{lastName}{firstName.Substring(0, i)}";

                exceedsMaxLength = samAccountName.Length > maxSamAccountNameLength;

                if (exceedsMaxLength)

                {

                    samAccountName = samAccountName.Substring(0, maxSamAccountNameLength);

                }

                samAccountNameExists = CheckUsernameExistsICON(samAccountName) || CheckUsernameExistsLPRA(samAccountName);

            }

 

            // if no available samaccountname was found using last name and characters from first name, add index number.

            // limit to a 1000 iterations.

            // make sure samaccountname does not exceed 20 characters.

            int x = 0;

            while (samAccountNameExists && x < 1000)

            {

                ++x;

                exceedsMaxLength = (samAccountName + x).Length > maxSamAccountNameLength;

               // var tempSamAccountName = exceedsMaxLength ? $"{samAccountName.Substring(0, maxSamAccountNameLength - x.ToString().Length)}{x}" : $"{samAccountName}{x}";

                samAccountNameExists = CheckUsernameExistsICON(tempSamAccountName) || CheckUsernameExistsLPRA(tempSamAccountName);

                if (!samAccountNameExists)

                {

                    samAccountName = tempSamAccountName;

                }

            }

 

 

            //int x = 0;

            //while (samAccountNameExists && x < 1000)

            //{

            //    ++x;

            //    var tempSamAccountName = $"{lastName}{firstName}{x}";

            //    samAccountNameExists = CheckUsernameExistsICON(tempSamAccountName) || CheckUsernameExistsLPRA(tempSamAccountName);

            //    samAccountName = tempSamAccountName;

            //}

 

            if (samAccountNameExists)

            {

                throw new Exception("Failed to generate a unique account name");

            }

 

            return samAccountName;

        }

}